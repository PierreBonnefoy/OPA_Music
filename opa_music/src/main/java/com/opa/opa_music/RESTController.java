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

    @RequestMapping("addfavvue")
    public ResponseEntity<String> addfav(@RequestBody Favorites v){
        JSONObject resp = new JSONObject();
        allFavorites.save(v);
        resp.put("status", 1);
        return ResponseEntity.ok(resp.toJSONString());
    }

    @RequestMapping("supprfavvue")
    public ResponseEntity<String> supprfavvue(@RequestBody Favorites v){
        JSONObject resp = new JSONObject();
        allFavorites.deleteByUserAndUrl(v.user,v.url);
        resp.put("status", 1);
        return ResponseEntity.ok(resp.toJSONString());
    }

    @RequestMapping("favvue")
    public List<Favorites> favvue(@RequestBody JSONObject user){
        return allFavorites.findAllByUser(user.get("username").toString());
    }

    @RequestMapping("addplaylist")
    public PlaylistTable addplaylist(@RequestBody PlaylistTable data){
        allPTab.save(data);
        return data;
    }

    @RequestMapping("addtoplaylist")
    public ResponseEntity<String> addtoplaylist(@RequestBody PlayList data){
        JSONObject resp = new JSONObject();
        allPlayList.save(data);
        return ResponseEntity.ok(resp.toJSONString());
    }

    @RequestMapping("/loadAll")
    public List<PlaylistTable> loadAll(@RequestBody JSONObject user){
        return allPTab.findAllByUser(user.get("username").toString());
    }

    @RequestMapping("/showPlayList")
    public List<PlayList> show(@RequestBody JSONObject data){
        return allPlayList.findAllByUserAndPid(data.get("username").toString(),(Integer) data.get("pid"));
    }

    @RequestMapping("/supprOfPlay")
    public ResponseEntity<String> supprOfPlay(@RequestBody PlayList p){
        JSONObject resp = new JSONObject();
        allPlayList.deleteByUserAndUrlAndPid( (String) p.user,(String) p.url,(Integer) p.pid);
        return ResponseEntity.ok(resp.toJSONString());
    }

    @RequestMapping("/supprPlay")
    public ResponseEntity<String>supprPlay(@RequestBody JSONObject data){
        JSONObject resp = new JSONObject();
        allPlayList.deleteByUserAndPid(data.get("user").toString(), (Integer) data.get("pid"));
        allPTab.deleteByUserAndId(data.get("user").toString(), (Integer) data.get("pid"));
        return ResponseEntity.ok(resp.toJSONString());
    }
}
