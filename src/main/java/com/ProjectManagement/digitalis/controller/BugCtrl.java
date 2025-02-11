package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.BugDto;
import com.ProjectManagement.digitalis.entitie.Bug;
import com.ProjectManagement.digitalis.entitie.BugStatus;
import com.ProjectManagement.digitalis.service.BugService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-02-09 00:15
 * @package com.ProjectManagement.digitalis.controller
 * @project digitalis
 */

@RestController
@RequestMapping("api/bugs")
public class BugCtrl {

    private final BugService bugService;
    String UPLOAD_DIR = "C:/Users/LIVE-TECH/Desktop/ProjectManagment/digitalis/imgCaptureBugs/";

    public BugCtrl(BugService bugService) {
        this.bugService = bugService;
    }

    @PostMapping("/report")
    public ResponseEntity<?> bugReport(@RequestBody BugDto bugDto){
        try {
            Bug bug = bugService.bugReport(bugDto);
            return ResponseEntity.ok(bug);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get/bugs")
    public ResponseEntity<List<Bug>> allBug(){
        try {
            List<Bug> bugs = bugService.getAllBugs();
            return ResponseEntity.ok(bugs);
        }catch (Exception e){
            return (ResponseEntity<List<Bug>>) ResponseEntity.notFound();
        }
    }

    @GetMapping("reportBySt/{idSt}")
    public ResponseEntity<List<Bug>> bugListBySt(@PathVariable Long idSt){
        System.out.println("id st: "+ idSt);
        try {
            List<Bug> bugList = bugService.getBugsBySt(idSt);
            if (bugList == null || bugList.isEmpty()) {
                bugList = new ArrayList<>();
                return ResponseEntity.ok(bugList);
            }
            return ResponseEntity.ok(bugList);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadImg")
    public ResponseEntity<String> uploadCapture(@RequestParam("captureUrl") MultipartFile file) {
        System.out.println("Requête reçue pour l'upload !");

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier est vide !");
        }

        try {

            // Vérifier si le dossier existe, sinon le créer
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs(); //  Crée le dossier s'il n'existe pas
                if (!created) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Impossible de créer le dossier de stockage !");
                }
            }

            // Générer un nom de fichier unique (pour éviter les conflits)
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File uploadFile = new File(uploadDir, fileName);

            // Sauvegarder le fichier sur le disque
            file.transferTo(uploadFile);
            String imageUrl = "http://localhost:8091/api/bugs/image/" + fileName;
            System.out.println("Fichier sauvegardé sous : " + uploadFile.getAbsolutePath());

            // Retourner l'URL complète du fichier
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de l'image !");
        }
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Change selon ton type d'image
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateBug/{id}/status")
    public ResponseEntity<Bug> updateBug(@PathVariable Long id, @RequestParam BugStatus status){
        try {
            Bug bug = bugService.ubdateBugStatus(id,status);
            return ResponseEntity.ok(bug);
        }catch (Exception e) {
            return (ResponseEntity<Bug>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
