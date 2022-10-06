package com.opa.opa_music;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
/* Class that represents a User */
public class Person {
    @Id
    @GeneratedValue
    @Getter
    private long id;

    public String login;
    // NOT DEFINITIVE BECAUSE ITS UNSECURE
    public String password;
    public String email;

    public Person(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
