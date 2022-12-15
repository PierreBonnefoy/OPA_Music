package com.opa.opa_music;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
/*
 * The Repository of Favorites for JPA
 */
public interface FavoritesRepository extends CrudRepository<Favorites, Long>{
    /* Show all favorites of a user */
    List<Favorites> findAllByUser(String user);

    /* Remove a music from user's favorites */
    @Transactional @Modifying
    Long deleteByUserAndUrl(String user, String url);
}
