package com.VaultDrop.VaultDropChatGptMiniProject.controller;


import com.VaultDrop.VaultDropChatGptMiniProject.model.Users;
import com.VaultDrop.VaultDropChatGptMiniProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {


    @Autowired
    AuthService service;


    @GetMapping("/")
    public String getHome(){
        return "WELCOME TO VAULTDROP HOME";
    }



    @GetMapping("/about")
    public String getAbout() {
        return "VaultDrop is an AI platform developed as a subsidiary of SportsRoundups Ltd. under the aegis of NBTECH";
    }


    @GetMapping("/status")
    public String getStatus() {
        return "Application is running fine!";
    }




    //ADMIN PORTION

    @GetMapping("/admin/dashboard")
    public String getDashBoard(){
        return "WELCOME TO ADMIN DASHBOARD";
    }


    @GetMapping("admin/users")
    public List<Users> getUsers(){
        return service.getAllUsers();
    }


    @GetMapping("/admin/users/{id}")
    public Users getUserById(@PathVariable Integer id){
        return service.getUserById(id);
    }


    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable Integer id){
        return  service.deleteUser(id);
    }




    //Account section

    @GetMapping("/account/my")
    public Users getMyAccount(Authentication authentication){
        String username=authentication.getName();
        return service.getUserByUsername(username);
    }


    @PutMapping("/account/update")
    public Users updateProfile(Authentication authentication,@RequestBody Map<String, String> data){  //Map is used for avoiding extra "" in data automatically assigned by json
        String username= authentication.getName();
        String newName=data.get("username");
        return service.updateProfile(username,newName);
    }


    @PutMapping("/account/updatePass")
    public Users changePassword(Authentication authentication,@RequestBody Map<String, String> data){
        String username=authentication.getName();
        String ePass=data.get("existPass");
        String nPass=data.get("newPass");
        return service.changePass(username,ePass,nPass);
    }



}
