package com.example.demo1.Post;

import com.example.demo1.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    //Main : Sub = Parent : Child = User : 0~n개의 Post들
    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private User user;
}
