package com.example.demo1.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//DB에 관련된 Bean
public interface UserRepository extends JpaRepository<User,Integer> {
    //이것 만으로도 JpaRepository에 있는 우리가 필요한 CRUD에 관한 메소드를 이용할 수 있다.
}
