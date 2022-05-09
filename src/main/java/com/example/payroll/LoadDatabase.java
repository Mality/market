package com.example.payroll;

import com.example.payroll.core.user.UserRepository;
import com.example.payroll.core.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, OrderRepository orderRepository) {

        return args -> {

//            User user1 = new User("Bilbo Baggins", "burglar", 100L);
//            User user2 = new User("Frodo Baggins", "thief", 1000L);
//
//            log.info("Preloading " + userRepository.save(user1));
//            log.info("Preloading " + userRepository.save(user2));
//
//            userRepository.findAll().forEach(user -> log.info("Preloaded " + user));
//
//            Order order1 = new Order(user1, "MacBook Pro", Status.COMPLETED, 10L);
//            Order order2 = new Order(user2, "iPhone", Status.IN_PROGRESS, 20L);

//            orderRepository.save(order1);
//            orderRepository.save(order2);
//
//            orderRepository.findAll().forEach(order -> log.info("Preloaded " + order));
        };
    }
}
