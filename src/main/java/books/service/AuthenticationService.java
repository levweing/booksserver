package books.service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.*;

public final class AuthenticationService {
    private static final long DURATION = TimeUnit.DAYS.toMillis(1);
    private static final String SECRET_KEY = "SecretKey";
    private static final String AUTHORIZATION_VALUE_PREFIX = "Bearer";
    
    public static void addToken(
        final HttpServletResponse httpResponse, final String username) {
        
        final JwtBuilder jwtBuilder = Jwts.builder().setSubject(username);
        
        jwtBuilder.setExpiration(
            new Date(System.currentTimeMillis() + DURATION));
        
        jwtBuilder.signWith(SignatureAlgorithm.HS512, SECRET_KEY);
        
        httpResponse.addHeader(HttpHeaders.AUTHORIZATION, String.format(
            "%s %s", AUTHORIZATION_VALUE_PREFIX, jwtBuilder.compact()));
        
        httpResponse.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
            HttpHeaders.AUTHORIZATION);
    }
    
    public static Optional<Authentication> getAuthentication(
        final HttpServletRequest httpRequest) {
        
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);

        final Optional<Claims> op = Optional.ofNullable(httpRequest.getHeader(
            HttpHeaders.AUTHORIZATION)).map(s -> jwtParser.parseClaimsJws(
            s.replace(AUTHORIZATION_VALUE_PREFIX, "")).getBody());
        
        final Optional<Authentication> result = op.map(Claims::getSubject).map(
            v -> new UsernamePasswordAuthenticationToken(
            v, null, Collections.emptyList()));
        
        return result;
    }
}
