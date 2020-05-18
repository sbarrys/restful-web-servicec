package com.example.demo1.User;

import com.example.demo1.Exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
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


//    @GetMapping(path="/v1/user/{id}") //PathVariable 로 들어온 string 형태의 id가 int 형태로 자동 변환되서 들어간다.
   // @GetMapping(value="/users/{id}",params="version=1")// /users/1/?version=1
    //@GetMapping(value="/users/{id}",headers="X-API-VERSION=1")
    @GetMapping(value="/v1/users/{id}",produces = "application/kty.company.appv1+json") //MIME TYPE
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
//    @GetMapping(path="/v2/user/{id}") //PathVariable 로 들어온 string 형태의 id가 int 형태로 자동 변환되서 들어간다.
 //   @GetMapping(value="/users/{id}",params="version=2")
//@GetMapping(value="/users/{id}",headers="X-API-VERSION=2")
@GetMapping(value="/v1/users/{id}",produces = "application/kty.company.appv2+json")//MIME TYPE //헤더에 Accept:~~작성
public MappingJacksonValue findByIdV2(@PathVariable int id){

        User user = userDao.findById(id);
        if(user==null){
            //RuntimeException을 상속받은 클래스를 해서 던져주었다.
            throw new UserNotFoundException(String.format("ID[%s] not Founded",id));
        }
        //V2와 관련된 uri 는 grade를 관리할 수 있는 기능이 있는것.
        User_v2 user_v2=new User_v2();
        BeanUtils.copyProperties(user,user_v2);//스프링 빈들간 작업을 할수있다.
        user_v2.setGrade("AAA");
        //필터링 만들기.
        SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");
        MappingJacksonValue mapping=new MappingJacksonValue(user);
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDomainClassv2",filter).setFailOnUnknownId(false);
        mapping.setFilters(filters);

        return mapping;
    }

}
