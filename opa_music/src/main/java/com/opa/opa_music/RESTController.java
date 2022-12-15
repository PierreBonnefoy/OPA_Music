package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import io.micrometer.core.ipc.http.HttpSender.Response;
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

    @Autowired
    private PlayListRepository allPlayList;

    @Autowired
    private PlayListTableRepository allPTab;

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
