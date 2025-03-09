package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.FichierDto;
import com.ProjectManagement.digitalis.entitie.Fichier;
import com.ProjectManagement.digitalis.entitie.Repertoir;
import com.ProjectManagement.digitalis.repositorie.FichierRepository;
import com.ProjectManagement.digitalis.repositorie.RepertoirRepository;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-02-18 11:29
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */

@Service

public class FichierService {

    private final FichierRepository fichierRepository;

    private final RepertoirRepository repertoirRepository;
    public FichierService(FichierRepository fichierRepository, RepertoirRepository repertoirRepository) {
        this.fichierRepository = fichierRepository;
        this.repertoirRepository = repertoirRepository;
    }

    public Fichier ajouterFichier(FichierDto fichierDto) throws IOException {
        Repertoir repertoire = repertoirRepository.findById(fichierDto.getRepertoireId())
                .orElseThrow(() -> new RuntimeException("Répertoire introuvable !"));

        String cheminFichier = Paths.get(repertoire.getCheminStockage(), fichierDto.getNom()).toString();
        String fileType = "";
        if(fichierDto.getNom() != null && fichierDto.getNom().contains(".")){
            fileType = fichierDto.getNom().substring(fichierDto.getNom().lastIndexOf(".")+1).toUpperCase();

        }
        // Sauvegarde physique du fichier
        File file = new File(cheminFichier);
        fichierDto.getFicher().transferTo(file);

        // Sauvegarde en base
        Fichier fichier = new Fichier();
        fichier.setNom(fichierDto.getNom());
        fichier.setCheminStockage(cheminFichier);
        fichier.setRepertoire(repertoire);
        fichier.setDescription(fichierDto.getDescription());
        fichier.setType(fileType);

        return fichierRepository.save(fichier);
    }

    public List<Fichier> getFileByRep(Long id){
            Repertoir repertoir = repertoirRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException(""));
            return fichierRepository.findByRepertoire(repertoir);
    }
}
