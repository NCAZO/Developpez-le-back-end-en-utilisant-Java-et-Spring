package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//	boolean existsByUsername(String username);

    boolean existsByName(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    Optional<User> findByName(String name);

}