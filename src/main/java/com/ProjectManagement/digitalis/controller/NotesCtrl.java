package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.NotesDto;
import com.ProjectManagement.digitalis.entitie.Notes;
import com.ProjectManagement.digitalis.service.NotesService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-03-16 15:04
 * @package com.ProjectManagement.digitalis.controller
 * @project digitalis
 */

@RestController
@Data
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NotesCtrl {

    private final NotesService notesService;

    @PostMapping("/create")
    public ResponseEntity<Notes> createNote(@RequestBody NotesDto notesDto){
        Notes notes = notesService.createNote(notesDto);

        return ResponseEntity.ok(notes);
    }

    @GetMapping("/getNote/{userId}")
    public ResponseEntity<List<Notes>> getNoteByUser(@PathVariable Long userId){
        List<Notes> notes = notesService.getNotsByUser(userId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(filename);
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

    @PutMapping("/update/{noteId}")
    public Notes updateNote(
            @PathVariable Long noteId,
            @RequestPart("notesDto") NotesDto notesDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        return notesService.updateNote(noteId, notesDto, imageFile);
    }

    @DeleteMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Long noteId){
        notesService.deleteNote(noteId);
        return "note supprimée avec succès!";
    }


}
