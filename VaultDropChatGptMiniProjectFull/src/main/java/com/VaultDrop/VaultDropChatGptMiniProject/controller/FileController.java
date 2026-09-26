package com.VaultDrop.VaultDropChatGptMiniProject.controller;


import com.VaultDrop.VaultDropChatGptMiniProject.model.File;
import com.VaultDrop.VaultDropChatGptMiniProject.model.Users;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.UserRepository;
import com.VaultDrop.VaultDropChatGptMiniProject.service.AuthService;
import com.VaultDrop.VaultDropChatGptMiniProject.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService service;


    @Autowired
    UserRepository userRepository;


    @Autowired
    AuthService authService;


    @GetMapping("/getmy")
    public List<File> getFiles(Authentication authentication) {

        String username = authentication.getName();

        Users user = authService.getUserByUsername(username);

        Integer ownerId = user.getId();

        return service.getMyFiles(ownerId);
    }


    @PostMapping("/upload")
    public File uploadFile(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {


        String username = authentication.getName();

        Users user = authService.getUserByUsername(username);

        Integer ownerId = user.getId();

        return service.uploadFile(file, ownerId);
    }







    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(
            @PathVariable Long id,
            Authentication authentication
    ) throws IOException {

        String username = authentication.getName();

        Users user = authService.getUserByUsername(username);

        Integer userId = user.getId();

        File fileEntity = service.getFileById(id);

        byte[] fileData = service.downloadFile(id, userId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileEntity.getFileName() + "\""
                )
                .contentType(
                        MediaType.parseMediaType(fileEntity.getFileType())
                )
                .body(fileData);
    }






    @DeleteMapping("/{id}")
    public String deleteFile(
            @PathVariable Long id,
            Authentication authentication
    ) throws IOException {

        String username = authentication.getName();

        Users user = authService.getUserByUsername(username);

        Integer ownerId = user.getId();

        service.deleteFile(id, ownerId);

        return "File deleted successfully";
    }






}
