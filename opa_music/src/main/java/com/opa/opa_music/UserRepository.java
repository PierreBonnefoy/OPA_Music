package com.opa.opa_music;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * User Repository in order to save into the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    //Method wich return a user by his name.
    Optional<User> findUserByName(String name);
}
