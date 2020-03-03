package com.mindgeek.samplecode.service;

import com.mindgeek.samplecode.domainobject.User;
import com.mindgeek.samplecode.domainvalue.UserType;
import com.mindgeek.samplecode.exception.ContentNotFoundException;
import com.mindgeek.samplecode.exception.DuplicateRecordException;
import com.mindgeek.samplecode.repository.UserRepository;
import com.mindgeek.samplecode.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AppUtil appUtil;



    public void registerUser(User user) throws DuplicateRecordException{

        if(null == userRepository.findByUsername((user.getUsername()))){
            //Encrypt Password
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setDeleted(false);
            user.setActive(true);
            userRepository.save(user);
        }else{
            throw new DuplicateRecordException("User already exists");
        }
    }

    public User getUser(String username, UserType type) throws ContentNotFoundException{
        User existingUser = userRepository.findByUsernameAndTypeAndIsDeleted(username, type, false);
        if(null == existingUser){
            throw new ContentNotFoundException("User not found");
        }else{
            return existingUser;
        }
    }
}
