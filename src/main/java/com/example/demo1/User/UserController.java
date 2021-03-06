package com.example.demo1.User;

import com.example.demo1.Exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilderDsl;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    //생성자를 이용한 UserDao Bean객체의 의존성주입
    private UserDao userDao;
    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    @GetMapping(path="/user")
    public MappingJacksonValue findAllUser(){
        List<User> users= userDao.findAll();
        //필터링 만들기.
        SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate");
        MappingJacksonValue mapping=new MappingJacksonValue(users);
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDomainClass",filter);
        mapping.setFilters(filters);

        return mapping;

    }
    @GetMapping(path="/user/{id}") //PathVariable 로 들어온 string 형태의 id가 int 형태로 자동 변환되서 들어간다.
    public EntityModel<User> findById(@PathVariable int id){

        User user = userDao.findById(id);
        if(user==null){
            //RuntimeException을 상속받은 클래스를 해서 던져주었다.
            throw new UserNotFoundException(String.format("ID[%s] not Founded",id));
        }
//        //필터링 만들기.
//        SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate");
//        MappingJacksonValue mapping=new MappingJacksonValue(user);
//        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDomainClass",filter);
//        mapping.setFilters(filters);
        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAllUser());
        model.add(linkTo.withRel("all-users"));
        return model;
    }
//Jackson 라이브러리를 사용해서 외부 노출 데이터를 필터링 할 수 있다.
    @PostMapping(path="/user")// 자바 객체가 아닌 JSON이나 xml 등 오브젝트형태를 받으려면 @RequestBody를 사용
    public ResponseEntity createUser(@Valid @RequestBody User user){
         User savedUser= userDao.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand((savedUser.getId())) //앞의{}에 넣어준다.
                .toUri();
        return ResponseEntity.created(location).build(); //객체이외의 것을 반환해주고자 할때 responseEntity를 이용해서 반환해준다.
    }

    @PutMapping(path="/user/{id}")
    public void updateUser(@RequestBody User user){
        //DB를 사용하지 않으므로 간단하게 대체하는 방식으로 사용햇다.
        userDao.update(user);
    }
    @DeleteMapping(path="/user/{id}")
    public void deleteUser(@PathVariable int id){
        userDao.deleteById(id);
    }
}
