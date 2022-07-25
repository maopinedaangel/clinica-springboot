package com.example.clinica.controller;

import com.example.clinica.model.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> user() throws Exception{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUser user = new AppUser();
        user.setName(userDetails.getUsername());

        return ResponseEntity.ok(user);
    }
}
