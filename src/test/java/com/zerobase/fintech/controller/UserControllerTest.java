package com.zerobase.fintech.controller;

import com.zerobase.fintech.user.domain.User;
import com.zerobase.fintech.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserRepository userRepository;


    @Test
    void createAccount(){
        //given
        User user = new User();

        //when
        userRepository.save(user);

        //then
        userRepository.findById()


    }

}