package com.trkpo.course.service;

import com.trkpo.course.entity.Credentials;
import com.trkpo.course.entity.CustomUserDetails;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.CredentialRepository;
import com.trkpo.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CredentialRepository credentialRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Credentials> credentials = credentialRepository.findByUserId(Long.valueOf(userId));
        if (credentials.isPresent()) {
            CustomUserDetails userDetails = new CustomUserDetails();
            userDetails.setLogin(credentials.get().getLogin());
            userDetails.setPassword(credentials.get().getPassword());
            return userDetails;
        } else {
            return null;
        }
    }
}
