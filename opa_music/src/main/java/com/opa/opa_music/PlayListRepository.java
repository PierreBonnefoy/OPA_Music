package com.opa.opa_music;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface PlayListRepository extends CrudRepository<PlayList, Long>{
    
    /* Show PlayList's musics */
    List<PlayList> findAllByUserAndPid(String user, Integer pid);

    /* Delete a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndPid(Integer pid, String user);

    /* Delete a musique from a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndUrlAndPid(Integer pid, String user, String url);


}
