package com.spring;

import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TVStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TVStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
            }
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                User admin = new User("admin", passwordEncoder.encode("adminpass"), roles);
                userRepository.save(admin);
            }
        };
    }
}