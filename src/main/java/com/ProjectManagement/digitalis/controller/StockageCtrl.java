package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.FichierDto;
import com.ProjectManagement.digitalis.dto.RepertoireDto;
import com.ProjectManagement.digitalis.entitie.Fichier;
import com.ProjectManagement.digitalis.entitie.Repertoir;
import com.ProjectManagement.digitalis.repositorie.RepertoirRepository;
import com.ProjectManagement.digitalis.service.FichierService;
import com.ProjectManagement.digitalis.service.RepertoirService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-02-18 12:49
 * @package com.ProjectManagement.digitalis.controller
 * @project digitalis
 */
@RestController
@RequestMapping("/api/stockage")
public class StockageCtrl {

    private final RepertoirService repertoirService;
    private final FichierService fichierService;
    private final RepertoirRepository repertoirRepository;

    public StockageCtrl(RepertoirService repertoirService, FichierService fichierService, RepertoirRepository repertoirRepository) {
        this.repertoirService = repertoirService;
        this.fichierService = fichierService;
        this.repertoirRepository = repertoirRepository;
    }

    @PostMapping("/repertoires/save")
    public ResponseEntity<Repertoir> creerRepertoire(@RequestBody RepertoireDto repertoireDto) {
        return ResponseEntity.ok(repertoirService.creerRepertoire(repertoireDto));
    }

    @PostMapping(value = "/file/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Fichier> ajouterFichier(
            @RequestParam("fichier") MultipartFile fichier,
            @RequestParam("nom") String nom,
            @RequestParam("repertoireId") Long repertoireId,
            @RequestParam(value = "description", required = false) String description
    ) throws IOException {
        FichierDto fichierDto = new FichierDto();
        fichierDto.setNom(nom);
        fichierDto.setRepertoireId(repertoireId);
        fichierDto.setFicher(fichier);
        fichierDto.setDescription(description);

        Fichier savedFile = fichierService.ajouterFichier(fichierDto);


        return ResponseEntity.ok().body(savedFile);
    }

    @GetMapping("/get/AllRepertoirs")
    public ResponseEntity<List<Repertoir>> getAllRepertoire(){
        try {
            List<Repertoir> repertoirs = repertoirService.lstRepertoir();
            return ResponseEntity.ok(repertoirs);
        }catch (Exception e){
            return (ResponseEntity<List<Repertoir>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRep/{id}")
    public ResponseEntity<Repertoir> getRepwhithsubRep(@PathVariable Long id){
        return repertoirService.getById(id).map(
                ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    @GetMapping("/getFile/{id}")
    public ResponseEntity<List<Fichier>> getFileByRepertoire(@RequestParam Long id){
        try {
            List<Fichier> files = fichierService.getFileByRep(id);
            return (ResponseEntity<List<Fichier>>) ResponseEntity.status(HttpStatus.OK);
        }catch (Exception e){
            return (ResponseEntity<List<Fichier>>) ResponseEntity.notFound();
        }
    }
}
