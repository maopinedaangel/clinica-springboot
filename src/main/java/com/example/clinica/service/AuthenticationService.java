package com.example.clinica.service;

import com.example.clinica.model.Rol;
import com.example.clinica.model.AppUser;
import com.example.clinica.repository.IAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    IAppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.getUserByName(userName);

        Set<GrantedAuthority> autorizaciones = new HashSet<>();
        GrantedAuthority autorizacion = null;
        for (Rol rol : user.get().getRoles()) {
            autorizacion = new SimpleGrantedAuthority(rol.getName());
            autorizaciones.add(autorizacion);
        }
        //org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(user.get().getName(),"{noop}" + user.get().getPassword(),true, true, true,true,autorizaciones);
        org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(),true, true, true,true,autorizaciones);
        return userDetail;
    }
}
