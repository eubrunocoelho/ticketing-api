package com.eubrunocoelho.ticketing.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider
{
    @Value( "${com.eubrunocoelho.ticketing.jwt.secret}" )
    private String jwtSecretKey;

    public String generateToken(
            Map<String, String> extractClaims,
            String username,
            long expireInterval
    )
    {
        return Jwts
                .builder()
                .claims()
                .add( extractClaims )
                .and()
                .subject( username )
                .issuedAt( new Date( System.currentTimeMillis() ) )
                .expiration( new Date( System.currentTimeMillis() + expireInterval ) )
                .signWith( getSignInKey() )
                .compact();
    }

    public String getUsername( String token )
    {
        Claims claims = extractAllClaims( token );

        return claims.getSubject();
    }

    private Claims extractAllClaims( String token )
    {
        return Jwts
                .parser()
                .verifyWith( getSignInKey() )
                .build()
                .parseSignedClaims( token )
                .getPayload();
    }

    private SecretKey getSignInKey()
    {
        byte[] keyBytes = jwtSecretKey.getBytes();

        return Keys.hmacShaKeyFor( keyBytes );
    }
}
