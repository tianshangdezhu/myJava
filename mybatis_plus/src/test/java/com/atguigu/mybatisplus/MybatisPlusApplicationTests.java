package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;



    @Test
    public void testInsert(){
        User user = new User(null, "张三", 23, "zhsangsan@qq.com", SexEnum.FEMALE,0);
        int i = userMapper.insert(user);
        System.out.println("受影响的行数："+i);
        System.out.println("id自动获取："+user.getId());
    }

    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(1624302720456450050L);
        System.out.println("受影响的行数："+result);
    }

    @Test
    public void testDeleteBatchByIds(){
        List<Long> idList = Arrays.asList( 4L, 5L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响的行数："+result);
    }

    @Test
    public void testDeleteByMap(){
//      配置了逻辑删除
//      UPDATE user SET is_deleted=1 WHERE name = ? AND age = ? AND is_deleted=0
        HashMap<String, Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name","Sandy");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响的行数："+result);
    }

    @Test
    public void testUpdateById(){
//      UPDATE user SET name=?, age=?, email=? WHERE id=? AND is_deleted=0
        User user = new User(5L, "admin", 35, "123@qq.com",SexEnum.MALE, 0);
        int result = userMapper.updateById(user);
        System.out.println("受影响的行数："+result);
    }


    @Test
    public void testSelectById(){
        User user = userMapper.selectById(5L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchByIds(){
//        SELECT id,name,age,email,is_deleted FROM user WHERE id IN ( ? , ? ) AND is_deleted=0
        List<Long> idList = Arrays.asList(4L, 5L);
        List<User> users = userMapper.selectBatchIds(idList);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",5L);
        map.put("name","admin");
        List<User> userList = userMapper.selectByMap(map);
        userList.forEach(System.out::println);

    }

    @Test
    public void testSelectList(){
        userMapper.selectList(null).forEach(System.out :: println);
    }





}
