package com.lq.sell.repository;

import com.lq.sell.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface UserRepository  extends JpaRepository<User,String>{

     User findUserByUserName(String username);
}
