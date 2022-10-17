package com.opa.opa_music;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FavoritesRepository extends CrudRepository<Favorites, Long>{

    List<Favorites> findAllByUser(String user);

    @Transactional @Modifying
    Long deleteByUserAndUrl(String user, String url);
}
