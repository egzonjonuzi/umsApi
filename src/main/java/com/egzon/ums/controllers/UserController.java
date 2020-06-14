/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egzon.ums.controllers;

import com.egzon.ums.entities.Users;
import com.egzon.ums.exceptions.UsernameExistsException;
import com.egzon.ums.repositories.UserRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author OMEN
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder password;

    @GetMapping
    public List<Users> list() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Users> getUser(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> insert(@RequestBody Users user) throws UsernameExistsException {
        
        Long founded = userRepository.findAll().stream().filter(filter -> filter.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())).count();
        
        if(founded>0L) {
           return new ResponseEntity("Unauthorized", HttpStatus.CONFLICT);
        } else {
            user.setPassword(password.encode(user.getPassword()));
            return ResponseEntity.ok(userRepository.save(user));
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return true;
    }

}
