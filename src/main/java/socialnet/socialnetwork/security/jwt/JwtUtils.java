package socialnet.socialnetwork.security.jwt;

import io.jsonwebtoken.*;
import socialnet.socialnetwork.security.AppUserDetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;

    public String generateJwtToken(AppUserDetails userDetails){
        return generateTokenFromUsername(userDetails.getUsername());
    }

    public String generateTokenFromUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validate(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            log.error("Invalid signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("Claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
