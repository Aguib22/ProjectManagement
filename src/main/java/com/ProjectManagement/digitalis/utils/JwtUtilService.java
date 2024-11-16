package com.ProjectManagement.digitalis.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtilService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;


    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // Méthode générique pour extraire une claim spécifique du token JWT.
    // Elle utilise une fonction (claimsResolver) qui spécifie quel claim extraire (comme le subject ou l'expiration).
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // Génère un token JWT simple pour un utilisateur donné sans claims supplémentaires.
    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }

    // Génère un token JWT en utilisant des claims supplémentaires et les détails de l'utilisateur.
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Retourne la durée d'expiration du JWT (en millisecondes).
    public long getExpirationTime() {
        return jwtExpiration;
    }

    // Méthode privée pour construire le token JWT.
    // Elle inclut les claims supplémentaires, le nom d'utilisateur, la date d'émission, et l'expiration.
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        // Construit et signe le JWT en utilisant l'algorithme HS256 et la clé secrète.
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Vérifie si le token est valide pour un utilisateur donné.
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Vérifie si le token JWT est expiré.
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    // Extrait la date d'expiration du token JWT.
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    // Extrait tous les claims (informations) contenus dans le token JWT.
    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Méthode pour obtenir la clé de signature utilisée pour signer et vérifier le token JWT.
    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

