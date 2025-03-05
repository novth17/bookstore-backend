package com.example.hnbookstore.service;

import com.example.hnbookstore.domain.BookUser;
import com.example.hnbookstore.domain.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * This class is used by Spring Security to authenticate and authorize users.
 **/
@Service
public class BookUserDetailsService implements UserDetailsService {

    @Autowired
    private BookUserRepository bookUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BookUser currUser = bookUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                currUser.getUsername(),
                currUser.getPassword(),  
                AuthorityUtils.createAuthorityList(currUser.getRole()) 
        );
    }
}
