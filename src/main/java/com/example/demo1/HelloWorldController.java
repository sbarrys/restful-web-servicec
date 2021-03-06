package com.example.demo1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }
    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        //spring 프레임워크는 JSON 형태로 변환해서 반환해준다.
        //Restcontroller는 @ResponseBody를 이용하지 않아도  객체를 직접 반환시켰을때 , JSON형태로 자동 변환해서 반환해준다.
         return new HelloWorldBean("Hello world");
    }
    //같은 이름의 함수여도, 매개 변수가 다르다.
    @GetMapping(path="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("path-variable : %s",name));
    }
}
