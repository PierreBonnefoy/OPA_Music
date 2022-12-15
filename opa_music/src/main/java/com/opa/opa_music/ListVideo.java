package com.opa.opa_music;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component 
/*
 * Create a list of Video Object to save them
 */
public class ListVideo{
    public List<Video> listVideo = new ArrayList<>();

    public ListVideo(){}

}
