package com.opa.opa_music;

import lombok.Data;

@Data 
/*
 * Save video's 'videoId'
 */
public class Video {
    String url;

    public Video(){}

    public Video(String url){
        this.url = url;
    }
}
