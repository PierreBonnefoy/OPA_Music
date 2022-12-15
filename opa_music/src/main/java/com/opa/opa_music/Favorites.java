package com.opa.opa_music;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorites")
/*
 * Class Entity for JPA of favorites and their owners
 */
public class Favorites {
    /* Id of the favorite */
    @Id
    @GeneratedValue
    @Column(name = "favorites_id")
    Integer id;

    /* Name of the owner */
    @Column(name = "user_name")
    @OrderColumn(name = "user_name")
    String user;

    /* Url of the music */
    @Column(name = "url")
    String url;

    public Favorites(String user,String link){
        this.user=user;
        this.url=link;
    }
}
