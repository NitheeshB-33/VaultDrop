package com.VaultDrop.VaultDropChatGptMiniProject.service;


import com.VaultDrop.VaultDropChatGptMiniProject.exception.ConflictException;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.InvalidCredentialsException;
import com.VaultDrop.VaultDropChatGptMiniProject.exception.UserNotFoundException;
import com.VaultDrop.VaultDropChatGptMiniProject.model.Users;
import com.VaultDrop.VaultDropChatGptMiniProject.repository.UserRepository;
import com.VaultDrop.VaultDropChatGptMiniProject.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    UserRepository repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    public Users register(Users user) {


        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException(
                    "Username already exists"
            );
        }

        String encodedPass= passwordEncoder.encode(user.getPassword());

        user.setRole("USER");
        user.setPassword(encodedPass);
        return repo.save(user);
    }




    public String login(Users user) {

        Users existing=repo.findByUsername(user.getUsername()).orElseThrow(()->

                new InvalidCredentialsException("Invalid username or password")

                );


        boolean passMatch=passwordEncoder.matches(user.getPassword(), existing.getPassword());

        if(!passMatch){
            throw new InvalidCredentialsException("Invalid Username or Password");
        }

        return jwtService.generateToken(existing.getUsername(), existing.getRole());



    }




    //ADMIN PORTION

    public List<Users> getAllUsers() {
        return repo.findAll();
    }

    public Users getUserById(Integer id) {
        return repo.findById(id).orElseThrow(()->
                    new UserNotFoundException(
                            "User not found with id: " + id
                    )
                );
    }

    public String deleteUser(Integer id) {

        if(!repo.existsById(id)){
            return "Not Exists";
        }
        repo.deleteById(id);
        return "Deleted Successfully";
    }




    //Account Section

    public Users updateProfile(String username, String newName) {

       Users exist=repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not Found"));

       exist.setUsername(newName);
        return repo.save(exist);
    }


    public Users changePass(String username,String ePass, String nPass) {

        Users exist=repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        boolean passMatch=passwordEncoder.matches(ePass,exist.getPassword());
        if(!passMatch){
            return null;
        }

        String encodedNewPass=passwordEncoder.encode(nPass);
        exist.setPassword(encodedNewPass);
        return repo.save(exist);
    }


    public Users getUserByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
    }



}
