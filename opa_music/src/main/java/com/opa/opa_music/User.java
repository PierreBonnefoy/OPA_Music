package com.opa.opa_music;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import lombok.Data;

/*
 * Class wich implements the modelization of a User and the Database Modelization.
 */
@Data
@Entity
@Table(name = "persons")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_passwd")
    private String password;

    @Column(name = "user_email")
    private String email;

    // Role managing NOT DONE
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_role")
    private List<String> roles;
}
