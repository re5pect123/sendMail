package com.online_exchange.spring.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.online_exchange.spring.model.User;
import com.online_exchange.spring.service.UserService;

public class SampleEmailApplication {

    public static void main(String[] args) {
        
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        UserService userService = (UserService) context.getBean("userService");
        userService.sendUserConfirmation(getUser());
        ((AbstractApplicationContext) context).close();
    }

    public static User getUser() {

        User user = new User();
        user.setEmail("marko.uljarevic@hotmail.com");
        user.setName("Marko");
        user.setPassword("123");

        return user;
    }

}
