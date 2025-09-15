package com.spring;

import com.spring.entity.TV;
import com.spring.service.TVService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class TVStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TVStoreApplication.class, args);
    }

}