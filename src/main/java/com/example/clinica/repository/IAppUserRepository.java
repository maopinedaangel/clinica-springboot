package com.example.clinica.repository;

import com.example.clinica.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IAppUserRepository extends JpaRepository<AppUser, Long> {

    /*
    @Query("from User u where u.name =:name")
    Optional<AppUser> getUserByName(@Param("name") String name);
    */
    @Query("from AppUser u where u.name =:name")
    Optional<AppUser> getUserByName(@Param("name") String name);

}
