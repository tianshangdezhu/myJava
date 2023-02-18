package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusProductTest {
    @Autowired
    private IProductService productService;

    @Test
    public void test01(){
        List<Product> list = productService.list();
        list.forEach(System.out::println);
    }
}
