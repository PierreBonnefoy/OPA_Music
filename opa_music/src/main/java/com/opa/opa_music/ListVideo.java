package com.opa.opa_music;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Create a list of Video Object
@Component 
public class ListVideo{
    public List<Video> listVideo = new ArrayList<>();

    public ListVideo(){}

}
