package com.spring.service;

import com.spring.entity.TV;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TVRestClient {
    private static final String BASE_URL = "http://localhost:8080/api/tvs";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TVRestClient::createGUI);
    }

    private static void createGUI() {
        JFrame frame = new JFrame("REST Client for TV");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton getAllButton = new JButton("GET All TVs");
        getAllButton.addActionListener(e -> getAllTVs());
        frame.add(getAllButton);

        JButton getByIdButton = new JButton("GET TV by ID");
        getByIdButton.addActionListener(e -> getTVById());
        frame.add(getByIdButton);

        JButton addButton = new JButton("POST Add TV");
        addButton.addActionListener(e -> addTV());
        frame.add(addButton);

        JButton updateButton = new JButton("PUT Update TV");
        updateButton.addActionListener(e -> updateTV());
        frame.add(updateButton);

        JButton deleteButton = new JButton("DELETE TV by ID");
        deleteButton.addActionListener(e -> deleteTV());
        frame.add(deleteButton);

        frame.setVisible(true);
    }

    private static void getAllTVs() {
        ResponseEntity<TV[]> response = restTemplate.getForEntity(BASE_URL, TV[].class);
        List<TV> tvs = Arrays.asList(response.getBody());
        JOptionPane.showMessageDialog(null, "TVs: " + tvs);
    }

    private static void getTVById() {
        String idStr = JOptionPane.showInputDialog("Enter ID:");
        long id = Long.parseLong(idStr);
        ResponseEntity<TV> response = restTemplate.getForEntity(BASE_URL + "/" + id, TV.class);
        JOptionPane.showMessageDialog(null, "TV: " + response.getBody());
    }

    private static void addTV() {
        TV tv = new TV("Samsung", "GALAXY", "OLED", 159.000, 10);
        HttpEntity<TV> request = new HttpEntity<>(tv);
        ResponseEntity<TV> response = restTemplate.postForEntity(BASE_URL, request, TV.class);
        JOptionPane.showMessageDialog(null, "Added TV: " + response.getBody());
    }

    private static void updateTV() {
        String idStr = JOptionPane.showInputDialog("Enter ID:");
        long id = Long.parseLong(idStr);
        TV tv = new TV("LG", "OLED", "OLED", 899.99, 5);
        HttpEntity<TV> request = new HttpEntity<>(tv);
        ResponseEntity<TV> response = restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.PUT, request, TV.class);
        JOptionPane.showMessageDialog(null, "Updated TV: " + response.getBody());
    }

    private static void deleteTV() {
        String idStr = JOptionPane.showInputDialog("Enter ID:");
        long id = Long.parseLong(idStr);
        restTemplate.delete(BASE_URL + "/" + id);
        JOptionPane.showMessageDialog(null, "TV deleted");
    }
}