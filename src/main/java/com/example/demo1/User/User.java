package com.example.demo1.User;

import com.example.demo1.Post.Post;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "User-Detail")
@Entity
//@JsonIgnoreProperties(value={"password","ssn"})
//@JsonFilter("UserDomainClass")
public class User {
    @Id
    @GeneratedValue//이를 기반으로 엔티티테이블 생성, 자동으로 생성되는 값
    private Integer id;
    @Size(min=2)
    @ApiModelProperty(notes="name : 이름 , 2글자 이상 ")
    private String name;
    @Past
    @ApiModelProperty(notes="name : 사용자의 등록일")
    private Date joinDate;
    //@JsonIgnore
    @ApiModelProperty(notes="name : 사용자의 비밀번호")
    private String password;
   // @JsonIgnore
   @ApiModelProperty(notes="name : 사용자의 개인 부여 번호")
   private String ssn;


    //유저한개당 포스트 0~n개//필수데이터 없어선 안됨.//post는 없어도됨 옵션데이터.
    @OneToMany(mappedBy = "user")
    private List<Post> post;

    public User(int id, String name, Date joinDate, String password, String ssn) {
    }
}
