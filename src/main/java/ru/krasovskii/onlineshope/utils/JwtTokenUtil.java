package ru.krasovskii.onlineshope.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        List<String> rolesList=userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles",rolesList);

        Date issuedData=new Date();
        Date expiredData=new Date(issuedData.getTime()+jwtLifetime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedData)
                .setExpiration(expiredData)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }
    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles",List.class);
    }

    private <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
