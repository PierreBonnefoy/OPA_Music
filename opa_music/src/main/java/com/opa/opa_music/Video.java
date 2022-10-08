package com.opa.opa_music;

import lombok.Data;

// Save video's 'videoId'
@Data 
public class Video {
    String url;

    public Video(){}

    public Video(String url){
        this.url = url;
    }
}
