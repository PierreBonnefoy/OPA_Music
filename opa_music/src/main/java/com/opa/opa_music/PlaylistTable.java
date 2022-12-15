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
@Table(name = "playlisttable")
@NoArgsConstructor
@AllArgsConstructor
/*
 * Class Entity for JPA of name and owner of a PlayList
 */
public class PlaylistTable {
    /* Id of a PlayList */
    @Id
    @GeneratedValue
    @Column(name = "playlist_id")
    Integer id;

    /* Owner of a PlayList */
    @Column(name = "user_name")
    @OrderColumn(name = "user_name")
    String user;

    /* Name of a PlayList */
    @Column(name = "playlist_name")
    String name;

    /* Constructor of this Class (use for database) */
    public PlaylistTable( String user,String name){
        this.user=user;
        this.name=name;
    }
}
