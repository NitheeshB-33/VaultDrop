package com.VaultDrop.VaultDropChatGptMiniProject.controller;


import com.VaultDrop.VaultDropChatGptMiniProject.dto.ShareRequest;
import com.VaultDrop.VaultDropChatGptMiniProject.dto.ShareResponse;
import com.VaultDrop.VaultDropChatGptMiniProject.model.File;
import com.VaultDrop.VaultDropChatGptMiniProject.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shares")
public class ShareController {


    @Autowired
    ShareService shareService;


    @PostMapping("/{fileId}")
    public void share(@PathVariable Long fileId, @RequestBody ShareRequest request, Authentication authentication){

        shareService.shareFile(fileId,request,authentication);

    }




    @GetMapping("/received")
    public List<File> received(Authentication authentication){
        return shareService.getReceivedShares(authentication);
    }



    @DeleteMapping("/{fileId}/{userId}")
    public void revoke(@PathVariable Long fileId, @PathVariable Integer userId, Authentication authentication){
        shareService.revokeShare(fileId, userId, authentication);
    }



    @GetMapping("/my")
    public List<ShareResponse> getMy(Authentication authentication){
        return shareService.getMyShare(authentication);
    }



}




