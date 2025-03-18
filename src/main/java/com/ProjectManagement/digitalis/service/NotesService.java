package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.NotesDto;
import com.ProjectManagement.digitalis.entitie.Notes;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.repositorie.NotesRepository;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import jdk.dynalink.linker.LinkerServices;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @author Aguibou sow
 * @date 2025-03-16 14:36
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */

@Service
@Data
@RequiredArgsConstructor
public class NotesService {

    String UPLOAD_DIR = "C:/Users/LIVE-TECH/Desktop/ProjectManagment/digitalis/notes";
    private final NotesRepository notesRepository;
    private final UserRepository userRepository;
    private final TacheService tacheService;



    public Notes createNote(NotesDto notesDto){
        User user = userRepository.findById(notesDto.getUser()).
                orElseThrow(()->new RuntimeException("user not found"));
        Notes notes = new Notes();
        notes.setUser(user);
        notes.setTitle(notesDto.getTitle());
        notes.setContent(notesDto.getContent());

        return notesRepository.save(notes);
    }

    public List<Notes> getNotsByUser(Long userId){
        return notesRepository.findByUserId(userId);
    }

    public String storeFile(MultipartFile file) throws IOException {
        // Créer le répertoire s'il n'existe pas
        File uploadPath = new File(UPLOAD_DIR);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Sauvegarder le fichier
        File uploadFile = new File(uploadPath, fileName);
        file.transferTo(uploadFile);
        String imageUrl = "http://localhost:8091/api/notes/image/" + fileName;


        return imageUrl;
    }


    public Notes updateNote(Long noteId, NotesDto notesDto,MultipartFile imageFile) throws IOException {
        Notes existingNote = notesRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        existingNote.setTitle(notesDto.getTitle());
        existingNote.setContent(notesDto.getContent());
        existingNote.setTache(notesDto.getTaches());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = this.storeFile(imageFile);
            existingNote.setImageUrl(imageUrl);
        }
        if (notesDto.getTaches() != null) {
            tacheService.createTache(notesDto.getTaches());
            existingNote.setTache(notesDto.getTaches());
        }

        return notesRepository.save(existingNote);
    }

    public void deleteNote(Long noteId){
        notesRepository.deleteById(noteId);
    }
}
