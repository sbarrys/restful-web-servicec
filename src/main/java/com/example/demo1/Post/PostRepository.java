package com.example.demo1.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//JPA는 명세이다. interface로 레포지토리 생성
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

}
