package com.test.kafkaTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by gexiaoshan on 2019/2/13.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.test.kafkaTest"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
