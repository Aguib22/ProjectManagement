package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.FichierDto;
import com.ProjectManagement.digitalis.dto.ProjectDto;
import com.ProjectManagement.digitalis.dto.RepertoireDto;
import com.ProjectManagement.digitalis.entitie.*;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.repositorie.GtRepository;
import com.ProjectManagement.digitalis.repositorie.ProjectUserRepository;
import com.ProjectManagement.digitalis.repositorie.ProjetRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.ProjetServices;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class ProjetServicesImpl implements ProjetServices {

    private final ProjetRepository projetRepository;
    private final GtRepository gtRepository;
    private final RepertoirService repertoirService;
    private final EntityManager entityManager;

    private final FichierService fichierService;
    private final EvolutionRepository evolutionRepository;
    private final ProjectUserRepository projectUserRepository;
    public ProjetServicesImpl(ProjetRepository projetRepository, GtRepository gtRepository, RepertoirService repertoirService, EntityManager entityManager, FichierService fichierService, EvolutionRepository evolutionRepository, ProjectUserRepository projectUserRepository) {
        this.projetRepository = projetRepository;
        this.gtRepository = gtRepository;
        this.repertoirService = repertoirService;
        this.entityManager = entityManager;
        this.fichierService = fichierService;

        this.evolutionRepository = evolutionRepository;
        this.projectUserRepository = projectUserRepository;
    }

    @Override
    public Projet saveProjet(Projet projet, MultipartFile fichierSpec, String fileName) throws ProjetError, IOException {
        if (projet == null || fichierSpec == null || fichierSpec.isEmpty()) {
            log.error("Projet ou fichier de spécification invalide");
            throw new ProjetError("Le projet ou le fichier de spécification est manquant !");
        }

        Projet projetSaved = projetRepository.save(projet);
        log.info("Projet enregistré : {}", projetSaved.getNomProjet());

        // Associer les utilisateurs au projet EN LES ENREGISTRANT DANS `ProjectUser`
     /*   Projet finalProjetSaved = projetSaved;
        List<ProjectUser> projectUsers = projet.getProjectUsers().stream().map(user ->
                ProjectUser.builder()
                        .user(user.getUser())
                        .projet(finalProjetSaved)
                        .enabled(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        ).toList();*/

        // Enregistrer les ProjectUser dans leur propre repository
        projectUserRepository.saveAll(projet.getProjectUsers());

        // Associer les utilisateurs au projet et sauvegarder encore

        projetSaved = projetRepository.save(projetSaved);

        RepertoireDto repertoireDto = new RepertoireDto();
        repertoireDto.setNom(projet.getNomProjet());
        repertoireDto.setDescription("Repertoire du projet "+projet.getNomProjet());
        Repertoir repertoir = repertoirService.creerRepertoire(repertoireDto);

        FichierDto fichierDto = new FichierDto();
        fichierDto.setNom(fileName);
        fichierDto.setDescription("Spécification fonctionnelles générale du projet "+projet.getNomProjet());
        fichierDto.setRepertoireId(repertoir.getId());

        fichierDto.setFicher(fichierSpec);

        Fichier fichier = fichierService.ajouterFichier(fichierDto);

        System.out.println(repertoir.getCheminStockage());
        return projetSaved;
    }

    @Override
    @Transactional
    public void editProjet(Long idProjet, ProjectDto projectDto) throws ProjetError {
        Projet projet = projetRepository.findById(idProjet).orElseThrow(() -> new ProjetError("Le projet à modifier n'existe pas"));
        /*if (optionalProjet.isEmpty()) {
            log.error("Le projet avec l'ID {} n'existe pas", idProjet);
            throw new ProjetError("Le projet à modifier n'existe pas");
        }

        Projet projet1 = optionalProjet.get();*/
        projet.setDescProjet(projectDto.getDescProjet());
        projet.setNomProjet(projectDto.getNomProjet());
        projet.setDateDebutProjet(projectDto.getDateDebutProjet());
        projet.setDateFinProject(projectDto.getDateFinProject());

        if(projectDto.getEvolution() != null){
            projet.setEvolution(projectDto.getEvolution());
        }

/*
        log.info("Modification du projet : {}", projet1.getNomProjet());
        return entityManager.merge(projet1);*/
    }

    @Override
    public Projet getProjet(Long idProjet) throws ProjetError {
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if (optionalProjet.isEmpty()) {
            log.error("Le projet recherché avec l'ID {} n'existe pas", idProjet);
            throw new ProjetError("Le projet rechercher n'existe pas");
        }
        log.info("Récupération du projet : {}", optionalProjet.get().getNomProjet());
        return optionalProjet.get();
    }

    @Override
    @Transactional
    public List<Projet> listProjet(Date startDate, Date endDate) {
        List<Projet> projets;

        if (startDate != null && endDate != null) {

            projets = projetRepository.findByDateDebutProjetBetween(startDate, endDate);

        } else {

            projets = projetRepository.findAll();
        }
        return projets;
    }

    @Override
    public void deleteProjet(Long idProjet) throws ProjetError {
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if (optionalProjet.isEmpty()) {
            log.error("Le projet avec l'ID {} à supprimer n'existe pas", idProjet);
            throw new ProjetError("Le projet à supprimer n'existe pas");
        }
        log.info("Suppression du projet avec l'ID : {}", idProjet);
        projetRepository.deleteById(idProjet);
    }

    @Override
    @Transactional
    public void updateProjetDates(Projet projet) throws GtError {
        List<GrandeTache> listGt = this.getGtByProjectId(projet.getIdProjet());
        Date min = listGt
                .stream()
                .min(Comparator.comparing(GrandeTache::getDateDeDebutGt))
                .map(GrandeTache::getDateDeDebutGt)
                .orElseThrow(() -> new GtError("Min date doesn't exist"));

        Date max = listGt
                .stream()
                .max(Comparator.comparing(GrandeTache::getDateDeFinGt))
                .map(GrandeTache::getDateDeFinGt)
                .orElseThrow(() -> new GtError("Max date doesn't exist"));
        projetRepository.updateStartAndEndDates(projet.getIdProjet(), min, max);
        /*List<GrandeTache> listGt = this.getGtByProjectId(projet.getIdProjet());
        projet.setListGt(listGt);

        if (listGt == null && listGt.isEmpty()) {
            projet.setListGt(new ArrayList<>());
        }


        Date dateDebutMin = projet.getListGt().stream()
                .map(GrandeTache::getDateDeDebutGt)
                .filter(Objects::nonNull)
                .min(Date::compareTo)
                .orElse(null);

        Date dateFinMax = projet.getListGt().stream()
                .map(GrandeTache::getDateDeFinGt)
                .filter(Objects::nonNull)
                .max(Date::compareTo)
                .orElse(null);

        projet.setDateDebutProjet(dateDebutMin);
        projet.setDateFinProject(dateFinMax);

        entityManager.merge(projet);*/  // Sauvegarde les nouvelles dates
    }

    @Override
    public List<GrandeTache> getGtByProjectId(Long projectId){
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(""));
        return gtRepository.findByProjet(projet);

    }

    @Override
    public List<User> getUsersByProjetId(Long projetId) {
        Optional<Projet> projetOptional = projetRepository.findById(projetId);
        if (projetOptional.isPresent()) {
            Projet projet = projetOptional.get();
            return projet.getProjectUsers().stream().map(ProjectUser::getUser).toList(); // Retourne la liste des utilisateurs associés au projet
        } else {
            throw new RuntimeException("Projet non trouvé avec l'ID : " + projetId);
        }
    }

    @Override
    public List<Projet> getProjetsByUserId(Long userId) {
        return projetRepository.findByUserId(userId);
    }

}
