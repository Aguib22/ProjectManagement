package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.RepertoireDto;
import com.ProjectManagement.digitalis.entitie.Repertoir;
import com.ProjectManagement.digitalis.repositorie.RepertoirRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * @author Aguibou sow
 * @date 2025-02-18 11:30
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */

@Service
public class RepertoirService {

    private final Path storageBasePath = Paths.get("C:/Users/LIVE-TECH/Desktop/ProjectManagment/digitalis_document");


    private final RepertoirRepository repertoirRepository;

    public RepertoirService(RepertoirRepository repertoirRepository){

        this.repertoirRepository = repertoirRepository;
    }


    public Repertoir creerRepertoire(RepertoireDto repertoireDto) {
        try {
            // Création de l'entité en base de données
            Repertoir repertoire = new Repertoir();
            repertoire.setNom(repertoireDto.getNom());
            repertoire.setDescription(repertoireDto.getDescription());

            Path cheminRepertoire;
            if (repertoireDto.getParentId() != null) {
                Repertoir parent = repertoirRepository.findById(repertoireDto.getParentId())
                        .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
                repertoire.setParent(parent);
                cheminRepertoire = Paths.get(parent.getCheminStockage(),repertoireDto.getNom());
            } else {
                // Répertoire racine dans "digitalis_document"
                cheminRepertoire = storageBasePath.resolve(repertoireDto.getNom());
            }

            //Création physique du dossier
            Files.createDirectories(cheminRepertoire);

            //Sauvegarde du chemin dans la base
            repertoire.setCheminStockage(cheminRepertoire.toString());

            return repertoirRepository.save(repertoire);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la création du répertoire", e);
        }
    }

    public List<Repertoir> lstRepertoir(){

        return repertoirRepository.findByParentIsNull();
    }

    public Optional<Repertoir> getById(Long id){
        return repertoirRepository.findById(id);
    }

}

