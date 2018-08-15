package com.lq.sell.service.impl;

import com.lq.sell.dataobject.User;
import com.lq.sell.repository.UserRepository;
import com.lq.sell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findById(String id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUserName(username);
    }
}
