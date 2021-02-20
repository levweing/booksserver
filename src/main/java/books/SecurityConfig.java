package books;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import books.service.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @SuppressWarnings("rawtypes")
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.cors();

        httpSecurity.addFilterBefore(
            new LoginFilter("/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilterBefore(new AuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);

        final AbstractRequestMatcherRegistry r =
            httpSecurity.authorizeRequests();

        ((AuthorizedUrl) r.antMatchers(HttpMethod.POST, "/login")).permitAll();
        ((AuthorizedUrl) r.anyRequest()).authenticated();
        
        // XXX: Allow access in DEV
//        ((AuthorizedUrl) r.anyRequest()).permitAll();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfig = new CorsConfiguration();
        
        corsConfig.setAllowCredentials(true);
        corsConfig.applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.DELETE);

        final UrlBasedCorsConfigurationSource result =
            new UrlBasedCorsConfigurationSource();
        
        result.registerCorsConfiguration("/**", corsConfig);
        return result;
    }

    @Autowired
    protected void configure(final AuthenticationManagerBuilder auth)
        throws Exception {
        
        auth.userDetailsService(userDetailsService).passwordEncoder(
            new BCryptPasswordEncoder());
    }
    
    private static final class AuthenticationFilter extends GenericFilterBean {
        @Override
        public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
            
            AuthenticationService.getAuthentication(
                (HttpServletRequest) request).ifPresent(v ->
                SecurityContextHolder.getContext().setAuthentication(v));
            
            chain.doFilter(request, response);
        }
    }
}
