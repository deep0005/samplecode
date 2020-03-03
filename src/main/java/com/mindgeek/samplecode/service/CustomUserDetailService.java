package com.mindgeek.samplecode.service;

import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainobject.User;
import com.mindgeek.samplecode.repository.DriverRepository;
import com.mindgeek.samplecode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        DriverDO driverDO = driverRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found for username " + username));

        return new org.springframework.security.core.userdetails.User(driverDO.getUsername(), driverDO.getPassword(), emptyList());
    }


    public void registerUser(User user){

        userRepository.save(user);
    }
}
