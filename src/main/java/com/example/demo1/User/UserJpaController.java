package com.example.demo1.User;

import com.example.demo1.Exception.UserNotFoundException;
import com.example.demo1.Post.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/user/{id}")
    public EntityModel<User> findById(@PathVariable int id){

        Optional<User> user= userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user.get());
        //전체 유저 찾는  uri 를 반환해준다
        WebMvcLinkBuilder linkTo =WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAllUser());
        model.add(linkTo.withRel("all-users"));
        return model;

    }
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser= userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/user/{id}/post")
    public List<Post> findAllPostByUserId(@PathVariable int id){
        //유저를 먼저 찾고 , 그 유저가 가지고 있는 게시물을 가져오면 된다.
        Optional<User> user= userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user.get().getPost();
    }
}
