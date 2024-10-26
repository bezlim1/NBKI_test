package com.example.nbki_test.repository;

import com.example.nbki_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);
    void deleteById (Integer id);
    User save (User user);

}
