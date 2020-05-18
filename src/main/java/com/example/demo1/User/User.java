package com.example.demo1.User;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

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
    private String name;
    @Past
    private Date joinDate;
    //@JsonIgnore
    private String password;
   // @JsonIgnore
    private String ssn;
}
