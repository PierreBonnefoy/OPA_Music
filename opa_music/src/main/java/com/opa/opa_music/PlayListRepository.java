package com.opa.opa_music;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

/*
 * The Repository of PlayList for JPA
 */
public interface PlayListRepository extends CrudRepository<PlayList, Long>{
    
    /* Show PlayList's musics */
    List<PlayList> findAllByUserAndPid(String user, Integer pid);

    /* Delete all music in a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndPid( String user,Integer pid);

    /* Delete a musique from a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndUrlAndPid(String user,  String url,Integer pid);


}
