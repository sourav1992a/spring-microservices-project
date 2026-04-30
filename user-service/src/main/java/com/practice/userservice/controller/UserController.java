package com.practice.userservice.controller;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    @Cacheable(value = "users", key = "#id")
    public String getUser(@PathVariable Long id) {

        System.out.println("Fetching from DB...");
        return "User ID: " + id;
    }
}