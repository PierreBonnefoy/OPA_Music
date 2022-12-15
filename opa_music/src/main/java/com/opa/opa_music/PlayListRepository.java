package com.opa.opa_music;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface PlayListRepository extends CrudRepository<PlayList, Long>{
    
    /* Show PlayList's musics */
    List<PlayList> findAllByUserAndId(String user, Integer id);

    /* Delete a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndId(Integer id, String user);

    /* Delete a musique from a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndUrlAndId(Integer id, String user, String url);


}
