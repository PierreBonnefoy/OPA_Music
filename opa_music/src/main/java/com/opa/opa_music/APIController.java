package com.opa.opa_music;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class APIController {
    
    ListVideo videos = new ListVideo();
    Search searchSpace = new Search();

    @Inject
    FavoritesRepository favoritesRepo;

    // Initialization of Search Object and ListVideo Object for the communication between java and html
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("search", searchSpace);
        model.addAttribute("videos", videos.listVideo);
        return "home";
    }

    // Call for a search
    @PostMapping("/search")
    public String search(String search){

        // Definition of all parameters used to create the search url
        String url = "https://www.googleapis.com/youtube/v3/search";
        String type = "video";
        String part = "snippet";
        int maxResults = 12;
        String key = "AIzaSyDbX-pXMguUhzBsu4a71kIIBFGvyKcjhuY";

        // Erase content 
        videos = new ListVideo();
        searchSpace.search = search;
        // Replace all whitespace by '+'
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
    
    // Save a fav
    @GetMapping("/addFav/{link}&{mail}")
    public String addFav(@PathVariable String link,@PathVariable String mail) {

        Favorites fav = new Favorites(mail,link);

        favoritesRepo.save(fav);

        return "redirect:/";
    }

    // Redirects to Favorites
    @GetMapping("/fav/{mail}")
    public String fav(@PathVariable String mail,Model model){

        // Erase content
        searchSpace = new Search();
        videos = new ListVideo();

        // Load favorites in 'videos'
        for(Favorites i : favoritesRepo.findAllByUser(mail)){
            videos.listVideo.add(new Video(i.url));
        }

        // Pass attributes to /fav
        model.addAttribute("search", searchSpace);
        model.addAttribute("videos", videos.listVideo);
        return "/fav";
    }

    // Remove 'link' Favorite of user 'mail'
    @GetMapping("/delFav/{link}&{mail}")
    public String delFav(@PathVariable String link,@PathVariable String mail) {

        favoritesRepo.deleteByUserAndUrl(mail, link);

        return ("redirect:/fav/"+mail);
    }

    @GetMapping("/clear")
    public String clear(){
        // Erase content
        searchSpace = new Search();
        videos = new ListVideo();
        return ("redirect:/");
    }

}
