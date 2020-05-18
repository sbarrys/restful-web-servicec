package com.example.demo1.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
    UserRepository userRepository;

    public UserJpaController(UserRepository userRepository){
        this.userRepository= userRepository;
    }
    @GetMapping("/user")
    public List<User> findAllUser(){

        return userRepository.findAll();

    }

}
