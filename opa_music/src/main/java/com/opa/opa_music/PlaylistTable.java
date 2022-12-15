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
public class PlaylistTable {
    @Id
    @GeneratedValue
    @Column(name = "playlist_id")
    Integer id;

    @Column(name = "user_name")
    @OrderColumn(name = "user_name")
    String user;

    @Column(name = "playlist_name")
    String name;

    public PlaylistTable( String user,String name){
        this.user=user;
        this.name=name;
    }
}
