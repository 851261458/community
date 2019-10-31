package com.mju.band3.community;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
        Properties properties=new Properties();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("test.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String a = properties.getProperty("a");
        System.out.println(a);

    }

}
