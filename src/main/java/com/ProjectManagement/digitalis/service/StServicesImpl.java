package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.*;
import com.ProjectManagement.digitalis.entitie.*;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.repositorie.*;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.StServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StServicesImpl implements StServices {

    private final StRepository stRepository;
    private final GtRepository gtRepository;
    private final EvolutionRepository evolutionRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final ProjetServicesImpl projetServices;

    private final BugService bugService;
    private final GtServices gtServices;
    private final RepertoirRepository repertoirRepository;
    private final RepertoirService repertoirService;
    private final UserTokenFmsService userTokenFmsService;
    private final FichierService fichierService;
    public StServicesImpl(StRepository stRepository, GtRepository gtRepository, EvolutionRepository evolutionRepository,
                          UserRepository userRepository, NotificationService notificationService, ProjetServicesImpl projetServices, BugService bugService, GtServices gtServices, ProjetRepository projetRepository, RepertoirRepository repertoirRepository, RepertoirService repertoirService, UserTokenFmsService userTokenFmsService, FichierService fichierService) {
        this.stRepository = stRepository;
        this.gtRepository = gtRepository;
        this.evolutionRepository = evolutionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.projetServices = projetServices;
        this.bugService = bugService;
        this.gtServices = gtServices;
        this.repertoirRepository = repertoirRepository;

        this.repertoirService = repertoirService;
        this.userTokenFmsService = userTokenFmsService;
        this.fichierService = fichierService;
    }

    @Override
    public SousTache saveSousTache(SousTacheRequest sousTacheRequest, MultipartFile fichier, String fileName) throws IOException {
        log.info("Enregistrement d'une nouvelle sous-tâche : {}", sousTacheRequest.getTacheSt());

        SousTache sousTache = new SousTache();
        sousTache.setNumeroSt(sousTacheRequest.getNumeroSt());
        sousTache.setTacheSt(sousTacheRequest.getTacheSt());
        sousTache.setChargesSt(sousTacheRequest.getChargesSt());
        sousTache.setDateDeDebutSt(sousTacheRequest.getDateDeDebutSt());
        sousTache.setDateDeFinSt(sousTacheRequest.getDateDeFinSt());
        sousTache.setRemarquesGt(sousTacheRequest.getRemarquesGt());
        sousTache.setPonderation((sousTacheRequest.getPonderation()));
        // Récupérer et associer les entités liées
        GrandeTache grandeTache = gtRepository.findById(sousTacheRequest.getIdGt())
                .orElseThrow(() -> new RuntimeException("Grande Tâche non trouvée"));
        Projet projet = grandeTache.getProjet();



        /*Evolution evolution = evolutionRepository.findById(sousTacheRequest.getIdEvolution())
                .orElseThrow(() -> new RuntimeException("Évolution non trouvée"));*/
        User dev = userRepository.findById(sousTacheRequest.getDev())
                .orElseThrow(() -> new RuntimeException("dev non trouvé"));
        String devTokenFmc = userTokenFmsService.getUserToken(dev.getIdUser());

        User testeur = userRepository.findById(sousTacheRequest.getTesteur())
                .orElseThrow(() -> new RuntimeException("testeur non trouvé"));
        String testeurTokenFmc = userTokenFmsService.getUserToken(testeur.getIdUser());

        sousTache.setGt(grandeTache);
        sousTache.setDev(dev);
        sousTache.setTesteur(testeur);
        sousTache.chargesStHeures(sousTacheRequest.getChargesSt());
        System.out.println(sousTache.getChargesStHeures());

        SousTache sousTacheSaved = stRepository.save(sousTache);

        if (grandeTache.getListSt() == null) {
            grandeTache.setListSt(new ArrayList<>());  // Initialiser la liste si elle est null
        }

        grandeTache.calculerChargesTotales();
        gtRepository.save(grandeTache);
        gtServices.updateGrandeTacheDates(grandeTache);
        //String notificationMsg = "Une nouvelle tâche vous a été assignée : " + sousTache.getTacheSt();
        notificationService.notifyTaskAssigned(sousTacheSaved.getTacheSt(), dev.getIdUser(),devTokenFmc);
        notificationService.notifyTaskAssigned(sousTacheSaved.getTacheSt(),testeur.getIdUser(),testeurTokenFmc);



        // Si un fichier est fourni, on l'enregistre
        if (fichier != null && !fichier.isEmpty()) {
            Repertoir repertoireProjet = repertoirRepository.findByNom(projet.getNomProjet());
            RepertoireDto repertoireDto = new RepertoireDto();
            repertoireDto.setNom(sousTache.getTacheSt());
            repertoireDto.setDescription("Répertoire de la sous-tâche " + sousTache.getTacheSt());
            repertoireDto.setParentId(repertoireProjet.getId());
            Repertoir repertoire = repertoirService.creerRepertoire(repertoireDto);

            FichierDto fichierDto = new FichierDto();
            fichierDto.setNom(fileName);
            fichierDto.setDescription("Document attaché à la sous-tâche " + sousTache.getTacheSt());
            fichierDto.setRepertoireId(repertoire.getId());
            fichierDto.setFicher(fichier);

            fichierService.ajouterFichier(fichierDto);
        }

        log.info("Sous-tâche enregistrée avec succès : {}", sousTache.getTacheSt());
        return sousTacheSaved;
    }

    @Override
    public Optional<SousTache> getSt(Long idSt) {
        log.info("Récupération de la sous-tâche avec l'ID : {}", idSt);

        return stRepository.findById(idSt);
    }

    @Override
    public SousTache editSt(Long idSt, StUpdateRequest st) throws ProjetError {
        log.info("Mise à jour de la sous-tâche avec l'ID : {}", idSt);

        SousTache sousTache = stRepository.findById(idSt)
                .orElseThrow(() -> new RuntimeException("Sous-tâche non trouvée"));

        // Mise à jour des champs de la sous-tâche

        sousTache.setTacheSt(st.getTacheSt());
        sousTache.setChargesSt(st.getChargesSt());
        sousTache.setDateDeDebutSt(st.getDateDeDebutSt());
        sousTache.setDateDeFinSt(st.getDateDeFinSt());
        sousTache.setDateDeFinReelleSt(st.getDateDeFinReelleSt());
        sousTache.setPonderation(st.getPonderation());

        sousTache.setRemarquesGt(st.getRemarquesGt());

        if(st.getEvolution() !=null){

            if(bugService.canMoveSousTache(sousTache.getIdSt())){
                sousTache.setEvolution(st.getEvolution());
            }else {
                bugService.updateSousTacheStatusIfBugPending(sousTache.getIdSt());
            }
        }


        // Mise à jour des relations avec les entités liées
        if (st.getIdGt() != null) {
            GrandeTache grandeTache = gtRepository.findById(st.getIdGt())
                    .orElseThrow(() -> new RuntimeException("Grande Tâche non trouvée"));
            sousTache.setGt(grandeTache);
        }
        GrandeTache grandeTache = sousTache.getGt();



        if (st.getIdUser() != null) {
            User dev = userRepository.findById(st.getIdUser())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            sousTache.setDev(dev);
        }

        SousTache sousTacheUpdated = stRepository.save(sousTache);
        grandeTache.calculerChargesTotales();
        gtServices.updateGrandeTacheDates(grandeTache);
        //bugService.updateSousTacheStatusIfBugPending(sousTacheUpdated.getIdSt());
        if(sousTacheUpdated.getEvolution().getIdTraitement() !=2){
            ProjectDto projectDto = new ProjectDto();
            projectDto.setEvolution(grandeTache.getEvolution());
            projectDto.setDateFinProject(grandeTache.getProjet().getDateFinProject());
            projectDto.setDateDebutProjet(grandeTache.getProjet().getDateDebutProjet());
            projectDto.setNomProjet(grandeTache.getProjet().getNomProjet());
            projectDto.setDescProjet(grandeTache.getProjet().getDescProjet());
            projetServices.editProjet(grandeTache.getProjet().getIdProjet(),projectDto);
        }
        log.info("Sous-tâche mise à jour avec succès : {}", sousTacheUpdated.getEvolution());
        if(sousTacheUpdated.getEvolution().getIdTraitement()==4){
            String testeurTokenFmc = userTokenFmsService.getUserToken(sousTacheUpdated.getTesteur().getIdUser());
            notificationService.notifyTaskSentForTesting(sousTacheUpdated.getTacheSt(),sousTacheUpdated.getTesteur().getIdUser(),testeurTokenFmc);
        }
        if (sousTacheUpdated.getEvolution().getIdTraitement() == 2){
            String devTokenFmc = userTokenFmsService.getUserToken(sousTacheUpdated.getDev().getIdUser());
            notificationService.notifyTaskCompleted(sousTacheUpdated.getTacheSt(),sousTacheUpdated.getDev().getIdUser(),devTokenFmc);
        }
        return sousTacheUpdated;
    }

    @Override
    public List<SousTache> listSt() {
        log.info("Récupération de la liste de toutes les sous-tâches");
        return stRepository.findAll();
    }

    @Override
    public void deleteSt(Long idSt) {
        log.info("Suppression de la sous-tâche avec l'ID : {}", idSt);

        if (stRepository.existsById(idSt)) {
            stRepository.deleteById(idSt);
            log.info("Sous-tâche supprimée avec succès : {}", idSt);
        } else {
            log.error("Aucune sous-tâche trouvée pour l'ID : {}", idSt);
            throw new RuntimeException("Aucune sous-tâche correspondante");
        }
    }

    @Override
    public SousTache updateSurchargeAndObservation(Long id, UpdateSurchargeAndObservation dto) {
        Optional<SousTache> optionalSousTache = stRepository.findById(id);
        if (optionalSousTache.isPresent()) {
            SousTache sousTache = optionalSousTache.get();

            // Mise à jour des nouveaux champs
            sousTache.setSurcharge(dto.getSurcharge());
            sousTache.setObservation(dto.getObservation());

            // Mise à jour automatique des champs dépendants
            sousTache.setChargesSt(sousTache.getChargesSt() + dto.getSurcharge());
            sousTache.setChargesStHeures(sousTache.getChargesStHeures() + (8 * dto.getSurcharge()));

            // Sauvegarde dans la base
            return stRepository.save(sousTache);
        } else {
            throw new RuntimeException("Sous-tâche introuvable avec l'ID : " + id);
        }
    }

    public Map<String,Long> countStByStatus(Date startDate, Date endDate){
        List<SousTache> sousTaches;
        if (startDate != null && endDate != null) {

            sousTaches = stRepository.findByDateDeDebutStBetween(startDate, endDate);
        } else {

            sousTaches = stRepository.findAll();
        }

        return sousTaches.stream().filter(
                st->st.getEvolution() != null
        ).collect(Collectors.groupingBy(
                st-> st.getEvolution().getEvolution(),
                Collectors.counting()
        ));
    }

    @Override
    public List<SousTache> getSousTachesByUserId(Long userId,Long idGt) {
        return stRepository.findSousTachesByUserIdAndGrandeTache(userId,idGt);
    }

}
