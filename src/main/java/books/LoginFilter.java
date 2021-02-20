package books;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import books.domain.AccountCredentials;
import books.service.AuthenticationService;

final class LoginFilter extends AbstractAuthenticationProcessingFilter {
    LoginFilter(
        final String processingUrl, final AuthenticationManager manager) {
        
        super(processingUrl);
        setAuthenticationManager(manager);
    }
    
    @Override
    protected final void successfulAuthentication(
        final HttpServletRequest request, final HttpServletResponse response,
        final FilterChain chain, final Authentication authResult) {
        
        AuthenticationService.addToken(response, authResult.getName());
    }

    @Override
    public final Authentication attemptAuthentication(
        final HttpServletRequest request,
        final HttpServletResponse response) throws IOException {
        
        final AccountCredentials ac = new ObjectMapper().readValue(
            request.getInputStream(), AccountCredentials.class);
        
        final Authentication result = getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
            ac.getUsername(), ac.getPassword(), Collections.emptyList()));
        
        return result;
    }
}
