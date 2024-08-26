package com.demo.user.management.service.impl;

import com.demo.user.management.service.JwtService;
import com.demo.user.management.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final long expiration = 1000 * 60 * 2;
    private final String secretKey = "BXotl5CPmvldzUUt6nph3Ub/chBxeLWP2ueJ8Ujfjus=";

    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("generateToken");
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        log.info("generateRefreshToken");
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("isTokenValid >> {}", token);
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        log.info("isTokenExpired >> {}", token);
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        log.info("claims >> {}", claims);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("token >> {}", token);
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
}
