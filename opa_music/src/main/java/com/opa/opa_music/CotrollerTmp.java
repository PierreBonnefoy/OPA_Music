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
public class CotrollerTmp {
    
    ListVideo videos = new ListVideo();

    @GetMapping("/")
    public String test(Model model){
        model.addAttribute("search", new Search());
        model.addAttribute("videos", videos.listVideo);
        return "research";
    }

    @PostMapping("/search")
    public String search(String search){

        String url = "https://www.googleapis.com/youtube/v3/search";
        String type = "video";
        String part = "snippet";
        int maxResults = 3;
        String key = "AIzaSyD5M5IzAQRkvydUZ12viKfkUzTwSa-BPAY";

        videos = new ListVideo();

        try{
            URL u = new URL(url+"?key="+key+"&type="+type+"&part="+part+"&maxResults="+maxResults +"&q="+search);
            URLConnection con = u.openConnection();
            con.connect();

            BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while((line = read.readLine())!=null){
                sb.append(line + "\n");
            }

            read.close();

            //System.out.println(sb.toString());
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
