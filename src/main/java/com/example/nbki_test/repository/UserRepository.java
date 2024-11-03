package com.example.nbki_test.repository;

import com.example.nbki_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

}
