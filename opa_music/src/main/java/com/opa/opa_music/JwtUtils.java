package com.opa.opa_music;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import org.slf4j.Logger;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    @Value("${jwt.jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.jwtExpiration}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }
        catch(SignatureException e){
            logger.error("ERROR TOKEN : Invalid Signature", e.getMessage());
        }
        catch(MalformedJwtException e){
            logger.error("ERROR TOKEN : Invalid Token", e.getMessage());
        }
        catch(ExpiredJwtException e){
            logger.error("ERROR TOKEN : Token has expired", e.getMessage());
        }
        catch(UnsupportedJwtException e){
            logger.error("ERROR TOKEN : Unsupported Token", e.getMessage());
        }
        catch(IllegalArgumentException e){
            logger.error("ERROR TOKEN : Jwt String is empty.", e.getMessage());
        }

        return false;
    }
}
