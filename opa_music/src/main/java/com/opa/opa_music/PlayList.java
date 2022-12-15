package com.opa.opa_music;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
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
public class PlayList {
    @Id
    @Column(name = "not_an_id")
    @GeneratedValue
    Integer not_an_id;

    @Column(name = "playlist_id") /* real id */
    Integer pid;

    @Column(name = "user_name")
    @OrderColumn(name = "user_name")
    String user;

    @Column(name = "url")
    String url;


}

