package books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import books.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(final String username) {
        final books.domain.User u = userRepository.findByUsername(username);
        
        final UserDetails result = new User(username, u.getPassword(),
            AuthorityUtils.createAuthorityList(u.getRole()));
        
        return result;
    }
}
