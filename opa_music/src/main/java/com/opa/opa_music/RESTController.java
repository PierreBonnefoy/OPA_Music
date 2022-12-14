package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import net.minidev.json.JSONObject;


import java.util.List;


@RestController
@RequestMapping("/api")
public class RESTController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;
    //Returning all users

    @Autowired
    private FavoritesRepository allFavorites; 

    @RequestMapping("/register")
    public ResponseEntity<String> doRegister(@RequestBody User user){
        JSONObject resp = new JSONObject();
        if (!userService.isUsernameAlreadyTake(user.getName())) {
            Integer id = userService.saveUser(user);
            resp.put("status", 1);
            return ResponseEntity.ok(resp.toString());
        }
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
}
