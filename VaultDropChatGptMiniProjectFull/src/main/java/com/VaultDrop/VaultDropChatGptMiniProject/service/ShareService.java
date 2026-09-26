package com.VaultDrop.VaultDropChatGptMiniProject.service;


import com.VaultDrop.VaultDropChatGptMiniProject.dto.ShareRequest;
import com.VaultDrop.VaultDropChatGptMiniProject.dto.ShareResponse;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.ConflictException;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.FileAccessDeniedException;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.FileNotFoundException;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.UserNotFoundException;
import com.VaultDrop.VaultDropChatGptMiniProject.model.File;
import com.VaultDrop.VaultDropChatGptMiniProject.model.FileShare;
import com.VaultDrop.VaultDropChatGptMiniProject.model.Users;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.FileRepository;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.FileShareRepository;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShareService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileShareRepository fileShareRepository;

    @Autowired
    UserRepository userRepository;




    public void shareFile(Long fileId, ShareRequest request, Authentication authentication) {

        String username=authentication.getName();
        Users user= userRepository.findByUsername(username).orElseThrow(()->
                        new UserNotFoundException("User not Found")
                );

        File file=fileRepository.findById(fileId).orElseThrow(()->
                    new FileNotFoundException("File not Found")
                );


        if(!file.getOwnerId().equals(user.getId())){
            throw new FileAccessDeniedException("You are not allowed to Use this file");
        }

        String recipient=request.getUsername();

        if(recipient.equals(user.getUsername())){
            throw new ConflictException("You cannot share a file with yourself");
        }


        if (request.getExpiresAt() == null ||
                !request.getExpiresAt().isAfter(LocalDateTime.now())) {

            throw new ConflictException(
                    "Expiration time must be in the future"
            );
        }



        Users recipientUser = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("Recipient not found")
                );



        if (fileShareRepository
                .findByFileIdAndSharedWithUserId(
                        fileId,
                        recipientUser.getId()
                )
                .isPresent()) {

            throw new ConflictException("File is already shared with this user");
        }




        FileShare fileShare = new FileShare();

        fileShare.setFileId(fileId);
        fileShare.setOwnerId(user.getId());
        fileShare.setSharedWithUserId(recipientUser.getId());
        fileShare.setExpiresAt(request.getExpiresAt());

        fileShareRepository.save(fileShare);





    }


    public List<File> getReceivedShares(Authentication authentication) {


        String username=authentication.getName();
        Users user= userRepository.findByUsername(username).orElseThrow(()->
                new UserNotFoundException("User not Found")
        );

        List<FileShare> share=fileShareRepository.findBySharedWithUserId(user.getId());


        List<File> files=new ArrayList<>();

        for(FileShare s: share){
            File file=fileRepository.findById(s.getFileId()).orElseThrow(()-> new FileNotFoundException("File not Found"));
            files.add(file);
        }
        return files;
    }




    public void revokeShare(Long fileId, Integer sharedWithUserId,
                            Authentication authentication){


        String username=authentication.getName();

        Users user=userRepository.findByUsername(username).orElseThrow(()->

                    new UsernameNotFoundException("User Not Found")
                );


        FileShare fileShare=fileShareRepository.findByFileIdAndSharedWithUserId(fileId,sharedWithUserId).orElseThrow(()->

                    new FileNotFoundException("Shared File not Found")
                );


        if(!fileShare.getOwnerId().equals(user.getId())){
            throw new FileAccessDeniedException("You are not allowed to revoke this share");
        }


        fileShareRepository.delete(fileShare);

    }



    public List<ShareResponse> getMyShare(Authentication authentication) {

        String username = authentication.getName();

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        List<FileShare> shares =
                fileShareRepository.findByOwnerId(user.getId());

        List<ShareResponse> response = new ArrayList<>();

        for (FileShare share : shares) {

            File file = fileRepository.findById(share.getFileId())
                    .orElseThrow(() ->
                            new FileNotFoundException("File not found")
                    );

            Users sharedUser = userRepository
                    .findById(share.getSharedWithUserId())
                    .orElseThrow(() ->
                            new UserNotFoundException("User not found")
                    );

            ShareResponse shareResponse = new ShareResponse(
                    share.getId(),
                    share.getFileId(),
                    file.getFileName(),
                    sharedUser.getUsername(),
                    share.getExpiresAt(),
                    sharedUser.getId()

            );

            response.add(shareResponse);
        }

        return response;
    }





}
