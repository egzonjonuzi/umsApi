/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egzon.ums.services;

import com.egzon.ums.entities.Users;
import com.egzon.ums.repositories.UserRepository;
import com.egzon.ums.security.MyUserPrincipal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author OMEN
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    public Users findUserByUsername(String username) {
        try {
            return (Users)em.createNamedQuery("Users.findByUsername")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
