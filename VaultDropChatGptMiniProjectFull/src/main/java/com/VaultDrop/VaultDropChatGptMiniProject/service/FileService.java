package com.VaultDrop.VaultDropChatGptMiniProject.service;


import com.VaultDrop.VaultDropChatGptMiniProject.exception.FileAccessDeniedException;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.FileNotFoundException;
import com.VaultDrop.VaultDropChatGptMiniProject.model.File;
import com.VaultDrop.VaultDropChatGptMiniProject.model.FileShare;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.FileRepository;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.FileShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    FileRepository repo;


    @Autowired
    FileShareRepository fileShareRepository;




    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; //10mb


    public File uploadFile(MultipartFile file, Integer ownerId) throws IOException {




        if(file.isEmpty()){
            throw new IllegalArgumentException("Uploaded file is Empty");
        }

        if(file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("File size exceeds the 10 MB limit");
        }




        String originalFileName = file.getOriginalFilename();


        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(
                    originalFileName.lastIndexOf(".")
            );
        }


        String uniqueFileName = UUID.randomUUID() + extension;


        Path uploadPath = Paths.get(uploadDir);


        Files.createDirectories(uploadPath);


        Path destinationPath = uploadPath.resolve(uniqueFileName);


        Files.copy(
                file.getInputStream(),
                destinationPath
        );


        File fileEntity = new File();

        fileEntity.setFileName(originalFileName);
        fileEntity.setFilePath(destinationPath.toString());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setOwnerId(ownerId);

        return repo.save(fileEntity);


    }


    public List<File> getMyFiles(Integer ownerId) {
        return repo.findByOwnerId(ownerId);
    }




    public byte[] downloadFile(Long fileId, Integer userId) throws IOException {

        File fileEntity = repo.findById(fileId)
                .orElseThrow(()->

                        new FileNotFoundException(
                                "File Not Available "+ fileId
                        )

                        );


        if (fileEntity.getOwnerId().equals(userId)) {
            Path filePath = Paths.get(fileEntity.getFilePath());
            return Files.readAllBytes(filePath);
        }

        FileShare fileShare = fileShareRepository
                .findByFileIdAndSharedWithUserId(fileId, userId)
                .orElseThrow(() ->
                        new FileAccessDeniedException(
                                "You are not allowed to download this file"
                        )
                );


        if (fileShare.getExpiresAt() != null &&
                !fileShare.getExpiresAt().isAfter(LocalDateTime.now())) {

            throw new FileAccessDeniedException(
                    "File sharing has expired"
            );
        }




        Path filePath = Paths.get(fileEntity.getFilePath());

        return Files.readAllBytes(filePath);
    }


    public File getFileById(Long id) {

        return repo.findById(id)
                .orElseThrow(()->

                        new FileNotFoundException(
                                "File Not Available "+ id
                        )

                );
    }



    public void deleteFile(Long fileId, Integer ownerId) throws IOException {

        File fileEntity = repo.findById(fileId)
                .orElseThrow(()->

                        new FileNotFoundException(
                                "File Not Available "+ fileId
                        )

                );


        if (!fileEntity.getOwnerId().equals(ownerId)) {
            throw new FileAccessDeniedException(
                    "You are not allowed to delete this file"
            );
        }


        Path filePath = Paths.get(fileEntity.getFilePath());


        Files.deleteIfExists(filePath);


        repo.delete(fileEntity);
    }




}
