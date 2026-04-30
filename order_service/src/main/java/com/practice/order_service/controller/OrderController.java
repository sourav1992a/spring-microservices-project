package com.practice.order_service.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.practice.order_service.client.UserClient;



import org.springframework.amqp.rabbit.core.RabbitTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/orders")
public class OrderController { @Autowired
private UserClient userClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 🔥 MAIN API
    @GetMapping("/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    public String createOrder(@PathVariable Long id) {

        // 🔹 Step 1: Call User Service (Feign)
        String user = userClient.getUser(id);

        // 🔹 Step 2: Send message to RabbitMQ
        String message = "Order created for " + user;
        rabbitTemplate.convertAndSend("order-queue", message);

        // 🔹 Step 3: Return response
        return "Order placed successfully for -> " + user;
    }

    // 🔥 FALLBACK METHOD (Circuit Breaker)
    public String fallbackUser(Long id, Exception ex) {

        // even if user service fails, order continues
        String fallbackMessage = "Order created (fallback) for user id: " + id;

        // send fallback message to queue
        rabbitTemplate.convertAndSend("order-queue", fallbackMessage);

        return "User service down! " + fallbackMessage;
    }

}