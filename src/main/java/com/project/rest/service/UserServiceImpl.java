package com.project.rest.service;

import com.project.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public String roleOfUser(String username)
    {
        return userRepository.findByUsername(username).getRoles().iterator().next().getName();
    }

    public Long idOfUser(String username)
    {
        return userRepository.findByUsername(username).getId();
    }
}
