package com.SRS.SRS.Security;

import com.SRS.SRS.Models.AdminEntity;
import com.SRS.SRS.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.Collections;


@Component
public class CustomAdminDetailService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity user;

        // Determine if username is an email
        if (username.contains("@")) {

            user = adminRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        } else {
            user = adminRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        }

        // Return UserDetails object

        System.out.println(user.getRole());
        return new User(
                user.getUsername(),  // or user.getEmail(), depending on login requirement
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))

        );
    }
}
