package com.opa.opa_music;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import java.util.List;
/*
 * The Repository of PlaylistTable for JPA
 */
public interface PlayListTableRepository extends CrudRepository<PlaylistTable, Long>{
    /* Show user's PlayLists */
    List<PlaylistTable> findAllByUser(String user);

    /* Delete a PlayList */
    @Transactional @Modifying
    Long deleteByUserAndId(String user, Integer id);
    
}
