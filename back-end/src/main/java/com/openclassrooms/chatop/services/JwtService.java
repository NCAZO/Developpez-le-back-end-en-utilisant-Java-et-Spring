package com.openclassrooms.chatop.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String SECRET_KEY = "28482B4D6251655468576D5A7134743777217A25432646294A404E635266556A";
    //  @Value("${oc.app.jwtSecret}")
//    private String jwtSecret = "lriAc1p6y27mg+Cy5TpDBh3USQ2CnFedOjp2kj9PE/ICFr6maDoahCPG2kGnsAFWhLANySppkBbHJxPyoIdchPXpP2WMHERZNY5LBCmmMj8B7N7TulyFTjLM72TC9JCGZe0ToYfIe5QNTeqzjU0wR1F2jhHdFBRrU7wikMEYoFoP7Mx8uJRxjotW4x2ngcM0XMKnoK6TgGyQ8IdoUI359Ain0BbrhrQXlZP5VeoPeXfrdz0UyeUQqTTwAok0zs4g4mBfxYm+W+2L9uc8H/mqkMfMcLjDzm+dLxxZRRlGdY3y3ctOpmvbuoTbCwlQ3rtlT1EYo2eg6cyn0//8RCKkjvmjVr2xsXNqSlr6aIxP66I=";
    //  @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs = 86400000;
    //  @Value("${oc.app.jwtCookieName}")
    private String jwtCookie = "nicolas";

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String getJwtFromAuthorizationHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Supprimer "Bearer " du début
        }
        return null;
    }

    public String generateToken(String username) {
        return generateTokenFromUsername(username);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
////                .signWith(key(), SignatureAlgorithm.HS256)
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        Date expirationDate = new Date((new Date()).getTime() + jwtExpirationMs);
//        if (expirationDate.before(new Date())) {
//            return false;
//        }
//        String username = extractUserName(token);
//        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
//    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String userName = extractUserName(authToken);
        return (userName != null && !isTokenExpired(authToken));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}