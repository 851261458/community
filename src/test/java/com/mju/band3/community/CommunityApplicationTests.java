package com.mju.band3.community;

import com.mju.band3.community.Mapper.QuestionMapper;
import com.mju.band3.community.enums.CommentTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Autowired
    QuestionMapper questionMapper;
    @Test
    public void fun() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("D:\\ITdeDOUDOU\\IdeaWorkPlace\\community\\src\\main\\resources\\generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    }
    @Test
    public void fun3(){
        CommentTypeEnum question = CommentTypeEnum.QUESTION;
        Integer type = question.getType();
        System.out.println(type);
    }

    @Test
    public void fun4(){
        String a="java,python";
        String[] split = StringUtils.split(a, ",");
        for (String s : split) {
            System.out.println(s);
        }

    }



}
