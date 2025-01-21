package org.example.dreamormhellospringboot3;

import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DreamOrmHelloSpringboot3Application {

    public static void main(String[] args) {
        SqlSession session;
        SpringApplication.run(DreamOrmHelloSpringboot3Application.class, args);
    }

}
