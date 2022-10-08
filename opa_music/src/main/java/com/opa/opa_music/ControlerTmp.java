package com.opa.opa_music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControlerTmp {
    
    ListVideo videos = new ListVideo();

    // Initialization of Search Object and ListVideo Object for the communication between java and html
    @GetMapping("/")
    public String test(Model model){
        model.addAttribute("search", new Search());
        model.addAttribute("videos", videos.listVideo);
        return "research";
    }

    // Call for a search
    @PostMapping("/search")
    public String search(String search){

        // Definition of all parameters used to create the search url
        String url = "https://www.googleapis.com/youtube/v3/search";
        String type = "video";
        String part = "snippet";
        int maxResults = 10;
        String key = "AIzaSyD5M5IzAQRkvydUZ12viKfkUzTwSa-BPAY";

        // Replace all whitespace by '+'
        videos = new ListVideo();
        search = search.replaceAll("\\s", "+");

        try{
            // Create the search url and make the connexion
            URL u = new URL(url+"?key="+key+"&type="+type+"&part="+part+"&maxResults="+maxResults +"&q="+search);
            URLConnection con = u.openConnection();
            con.connect();

            // Get all informations contain in con
            BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = read.readLine())!=null){
                sb.append(line + "\n");
            }
            read.close();

            // Select 'videoId' of the first 'maxResults' videos and add it to 'videos'
            JSONObject jObject = new JSONObject(sb.toString());
            for(int i=0;i<maxResults;i++){
                videos.listVideo.add(new Video(jObject.getJSONArray("items").getJSONObject(i).getJSONObject("id").get("videoId").toString()));
            }
            

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        

        return "redirect:/";
    }
}
