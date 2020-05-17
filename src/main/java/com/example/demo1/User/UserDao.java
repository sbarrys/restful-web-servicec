package com.example.demo1.User;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDao {
    //사용자 정보 조작해서 비즈니스 로직 구현
    private static List<User> users = new ArrayList<>();
    private static int UserCount=3;

    static{
        users.add(new User(1,"kty",new Date()));
        users.add(new User(2,"hgd",new Date()));
        users.add(new User(3,"lyg",new Date()));
    }
    public List<User> findAll(){
        return users;
    }
    public User findById(int id){
        for(User user : users){ //user의 목록에 id가 동일한 사람이 있으면 반환
            if(user.getId()==id){
                return user;
            }
        }
        return null;//못찾으면 null반환

    }
    public User save(User user){
        user.setId(++UserCount);
        //DB의 역할을 하는 list 에 추가.
        users.add(user);
        return user;
    }

    public void update(User user_) {
        int id_=user_.getId();
        int idx_=0;
        Iterator<User> it =users.iterator();
        while(it.hasNext()){
            User user= it.next();
            if(user.getId()==id_){
                it.remove();
                users.add(user_);
                return;
            }
        }
        return;
    }
    public User deleteById(int id_){
        Iterator<User> it=  users.iterator();
        while(it.hasNext()){
            User user= it.next();
            if(user.getId()==id_){
                it.remove();
                return user;
            }

        }
        return null;
    }
}
