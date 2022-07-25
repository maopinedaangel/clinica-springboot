package com.example.clinica;

import com.example.clinica.model.Rol;
import com.example.clinica.model.AppUser;
import com.example.clinica.repository.IRolRepository;
import com.example.clinica.repository.IAppUserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(DataLoader.class);

    private IAppUserRepository userRepository;

    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(IAppUserRepository userRepository, IRolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Rol rolUsuario = rolRepository.save(new Rol("usuario"));
        Rol rolAdmin = rolRepository.save(new Rol("admin"));
        logger.info("Data Loader corriendo");

        String password1 = passwordEncoder.bCryptPasswordEncoder().encode("password1");
        String password2 = passwordEncoder.bCryptPasswordEncoder().encode("password2");

        Set<Rol> rolesUsuario1 = new HashSet<>();
        rolesUsuario1.add(rolUsuario);
        Set<Rol> rolesUsuario2 = new HashSet<>();
        rolesUsuario2.add(rolAdmin);
        userRepository.save(new AppUser("Mauricio", "Pineda", "maopineda", "ingmauriciopineda@gmail.com", password1, rolesUsuario1));
        userRepository.save(new AppUser("Juliet", "Berrío", "jberrio", "jberrio@gmail.com", password2, rolesUsuario2));
    }
}
