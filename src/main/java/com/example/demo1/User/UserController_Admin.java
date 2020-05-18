package com.example.demo1.User;

import com.example.demo1.Exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController_Admin {
    //생성자를 이용한 UserDao Bean객체의 의존성주입
    private UserDao userDao;
    public UserController_Admin(UserDao userDao){
        this.userDao = userDao;
    }

    @GetMapping(path="/user")
    public MappingJacksonValue findAllUser(){
        List<User> users= userDao.findAll();
        //필터링 만들기.
        SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","password","ssn");
        MappingJacksonValue mapping=new MappingJacksonValue(users);
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDomainClass",filter);
        mapping.setFilters(filters);

        return mapping;
    }


    @GetMapping(path="/user/{id}") //PathVariable 로 들어온 string 형태의 id가 int 형태로 자동 변환되서 들어간다.
    public MappingJacksonValue findById(@PathVariable int id){

        User user = userDao.findById(id);
        if(user==null){
            //RuntimeException을 상속받은 클래스를 해서 던져주었다.
            throw new UserNotFoundException(String.format("ID[%s] not Founded",id));
        }
        //필터링 만들기.
        SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","password","ssn");
        MappingJacksonValue mapping=new MappingJacksonValue(user);
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDomainClass",filter);
        mapping.setFilters(filters);

        return mapping;
    }

}
