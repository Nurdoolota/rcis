package com.spring.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AdminMessageListener {
    @JmsListener(destination = "adminQueue")
    public void receiveMessage(String message) {
        System.out.println("=== Административное уведомление ===");
        System.out.println(message);
        System.out.println("=====================================");
    }
}