package com.example.nbki_test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private int id;

    @Column (name = "name")
    private String name;

    @Column (name = "surname")
    private String surname;
}
