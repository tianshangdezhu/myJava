package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusUserWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        //SELECT id,name,age,email,is_deleted FROM user
        // WHERE is_deleted=0 AND (name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","a")
                .between("age",20,10)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test02(){
        //SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 ORDER BY age DESC,id ASC
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.orderByDesc("age")
                .orderByAsc("id");
        List<User> users = userMapper.selectList(userWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test03(){
        //UPDATE user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.isNull("email");
        int result = userMapper.delete(userWrapper);
        System.out.println("受影响的行数："+result);
    }

    @Test
    public void test04(){
        //UPDATE user SET age=? WHERE is_deleted=0 AND (name LIKE ? AND age > ? OR email IS NULL)
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name","a")
                .gt("age",10)
                .or()
                .isNull("email");
        User user = new User();
        user.setAge(18);
        int result = userMapper.update(user, userQueryWrapper);
        System.out.println("受影响的行数："+result);
    }

    @Test
    public void test05(){
        //SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (id NOT IN (select id from user where id >= 4))
        //SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (id IN (select id from user where id >= 4))
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("age","name");
       // userQueryWrapper.notInSql("id","select id from user where id >= 4");
        userQueryWrapper.inSql("id","select id from user where id >= 4");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test06(){
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("age",18)
                .set("name","lisi")
                .like("name","zhangsan")
                .and(i ->i.gt("age",25).or().isNull("email"));
        int result = userMapper.update(null, userUpdateWrapper);
        System.out.println(result);
    }

    @Test
    public void test07(){
        String name = null;
        Integer ageBegin = 19;
        Integer ageEnd = 25;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            userQueryWrapper.like("name","a");
        }
        if (ageBegin != null) {
            userQueryWrapper.ge("age",ageBegin);
        }
        if (ageEnd != null) {
            userQueryWrapper.lt("age",ageEnd);
        }
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test08() {
        String name = null;
        Integer ageBegin = 19;
        Integer ageEnd = 25;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("name","age");
        userQueryWrapper.like(StringUtils.isNotBlank(name),"name",name)
                .gt(ageBegin != null,"age",ageBegin)
                .lt(ageEnd != null,"age",ageEnd);
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test09() {
        String name = null;
        Integer ageBegin = 19;
        Integer ageEnd = 25;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),User::getName,name)
                .gt(ageBegin !=null,User::getAge,ageBegin)
                .lt(ageEnd != null,User::getAge,ageEnd);
        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    @Test
    public void test10(){
        // Preparing: UPDATE user SET age=?,name=?
        // WHERE is_deleted=0 AND (name LIKE ? AND (age >= ? AND age < ? OR email IS NOT NULL))
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAge,10)
                .set(User::getName,"wangwu")
                .like(User::getName,"zhang")
                .and(i ->i.ge(User::getAge,21).lt(User::getAge,22).or().isNotNull(User::getEmail));
        userMapper.update(null,updateWrapper);
    }







}
