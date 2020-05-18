package com.example.demo1.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password","ssn"})
public class User {

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
