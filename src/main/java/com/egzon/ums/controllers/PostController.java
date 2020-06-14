/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egzon.ums.controllers;

import com.egzon.ums.entities.Posts;
import com.egzon.ums.repositories.PostRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/post")
public class PostController {
    
    @Autowired
    PostRepository postRepository;
    
    @GetMapping()
    public List<Posts> list() {
        return postRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Posts> get(@PathVariable Long id) {
        return postRepository.findById(id);
    }
    
    @PostMapping(path="/insert", consumes = "application/json", produces = "application/json")
    public Posts insert(@RequestBody Posts post) {
        return postRepository.save(post);
    }
    
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
         postRepository.deleteById(id);
         return true;
    }
    
}
