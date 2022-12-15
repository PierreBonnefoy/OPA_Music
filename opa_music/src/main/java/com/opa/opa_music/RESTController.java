package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import net.minidev.json.JSONObject;


import java.util.List;

/*
 * Class wich implements the method in order to generate the REST API.
 */
@RestController
//Address of the API.
@RequestMapping("/api")
public class RESTController {
    //Some importants repositories imports.
    @Autowired
    private IUserService userService;

    @Autowired
    private FavoritesRepository allFavorites; 

    @Autowired
    private PlayListRepository allPlayList;

    @Autowired
    private PlayListTableRepository allPTab;

    //Method wich is implementing the API for the registering system.
    @RequestMapping("/register")
    public ResponseEntity<String> doRegister(@RequestBody User user){
        //Creation of the JSON response object.
        JSONObject resp = new JSONObject();

        //Verification of the unicity of the username.
        if (!userService.isUsernameAlreadyTake(user.getName())) {
            //Saving the user into the database.
            userService.saveUser(user);

            //Generation of the response. 
            resp.put("status", 1);
            return ResponseEntity.ok(resp.toString());
        }

        //Generation of the error response. 
        resp.put("status", 0);
        return ResponseEntity.ok(resp.toString());
    }

    /* Method of adding v to Favorites table */
    @RequestMapping("addfavvue")
    public ResponseEntity<String> addfav(@RequestBody Favorites v){
        JSONObject resp = new JSONObject();
        /* Saving the the music */
        allFavorites.save(v);
        resp.put("status", 1);
        return ResponseEntity.ok(resp.toJSONString());
    }

    /* Method of delete a music v of Favorites table */
    @RequestMapping("delfavvue")
    public ResponseEntity<String> delfavvue(@RequestBody Favorites v){
        JSONObject resp = new JSONObject();
        /* Delete the music from Favorites table */
        allFavorites.deleteByUserAndUrl(v.user,v.url);
        resp.put("status", 1);
        return ResponseEntity.ok(resp.toJSONString());
    }

    /* Methode get all Favorites of the current user */
    @RequestMapping("favvue")
    public List<Favorites> favvue(@RequestBody JSONObject user){
        return allFavorites.findAllByUser(user.get("username").toString());
    }

    /* Mathode adding a PlayList for the current user */
    @RequestMapping("addplaylist")
    public PlaylistTable addplaylist(@RequestBody PlaylistTable data){
        /* Save in the new PlayList */
        allPTab.save(data);
        return data;
    }

    /* Methode adding a music to a PlayList of the current user */
    @RequestMapping("addtoplaylist")
    public ResponseEntity<String> addtoplaylist(@RequestBody PlayList data){
        JSONObject resp = new JSONObject();
        /* Save the music in the PlayList */
        allPlayList.save(data);
        return ResponseEntity.ok(resp.toJSONString());
    }

    /* Methode get all PlayLists of the current urser */
    @RequestMapping("/loadAll")
    public List<PlaylistTable> loadAll(@RequestBody JSONObject user){
        return allPTab.findAllByUser(user.get("username").toString());
    }

    /* Methode get all music in current PlayList */
    @RequestMapping("/showPlayList")
    public List<PlayList> show(@RequestBody JSONObject data){
        return allPlayList.findAllByUserAndPid(data.get("username").toString(),(Integer) data.get("pid"));
    }

    /* Methode delete a music from the current PlayList */
    @RequestMapping("/delOfPlay")
    public ResponseEntity<String> delOfPlay(@RequestBody PlayList p){
        JSONObject resp = new JSONObject();
        allPlayList.deleteByUserAndUrlAndPid( (String) p.user,(String) p.url,(Integer) p.pid);
        return ResponseEntity.ok(resp.toJSONString());
    }

    /* Methode delete all current PlayList */
    @RequestMapping("/delPlay")
    public ResponseEntity<String>delPlay(@RequestBody JSONObject data){
        JSONObject resp = new JSONObject();
        allPlayList.deleteByUserAndPid(data.get("user").toString(), (Integer) data.get("pid"));
        allPTab.deleteByUserAndId(data.get("user").toString(), (Integer) data.get("pid"));
        return ResponseEntity.ok(resp.toJSONString());
    }
}
