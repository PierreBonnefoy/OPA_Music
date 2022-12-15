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
@Table(name = "playlist")
@NoArgsConstructor
@AllArgsConstructor
/*
 * Class Entity for JPA of url of PlayLists
 */
public class PlayList {
    /* Useless but if you remove it, you crsh the project */
    @Id
    @Column(name = "not_an_id")
    @GeneratedValue
    Integer not_an_id;
    
    /* The Id of the PlayList where the music is located */
    @Column(name = "playlist_id") 
    Integer pid;

    /* The name user who owned the PlayList */
    @Column(name = "user_name")
    @OrderColumn(name = "user_name")
    String user;

    /* The url of the music */
    @Column(name = "url")
    String url;


}

