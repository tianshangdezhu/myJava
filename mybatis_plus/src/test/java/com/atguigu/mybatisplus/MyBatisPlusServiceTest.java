package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MyBatisPlusServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUserCount(){
        //SELECT COUNT( * ) FROM user WHERE is_deleted=0
        long usercount = userService.count();
        System.out.println("总人数："+usercount);
    }

    @Test
    public void testInsetMore(){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(20+i);
            user.setEmail("zhangsan"+i+"qq.com");
            user.setName("zhangsan"+i);
            users.add(user);
        }
        boolean b = userService.saveBatch(users);
        System.out.println(b);
    }

    @Test
    public void testSelectAll(){
        List<User> users = userService.list();
        users.forEach(System.out::println);
    }

    @Test
    public void testDeleteAll(){
        userService.remove(null);
    }


}
