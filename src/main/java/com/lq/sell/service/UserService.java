package com.lq.sell.service;

import com.lq.sell.dataobject.User;

public  interface UserService {

    User findById(String id);

    User  findUserByUsername(String username);
}
