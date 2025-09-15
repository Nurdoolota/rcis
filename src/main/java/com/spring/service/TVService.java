package com.spring.service;

import com.spring.entity.TV;
import com.spring.repository.TVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TVService {
    private final TVRepository repository;

    @Autowired
    public TVService(TVRepository repository) {
        this.repository = repository;
    }

    public TV addTV(TV TV) {
        if (TV.getPrice() <= 0) throw new IllegalArgumentException("Цена должна быть >0");
        return repository.save(TV);
    }

    public List<TV> getAllTV() {
        return repository.findAll();
    }

    public Optional<TV> getTVById(Long id) {
        return repository.findById(id);
    }

    public TV updateTV(Long id, TV updatedTV) {
        return repository.findById(id).map(TV -> {
            TV.setManufacturer(updatedTV.getManufacturer());
            TV.setModel(updatedTV.getModel());
            TV.setScreenType(updatedTV.getScreenType());
            TV.setPrice(updatedTV.getPrice());
            TV.setQuantity(updatedTV.getQuantity());
            return repository.save(TV);
        }).orElseThrow(() -> new IllegalArgumentException("Телевизор не найден"));
    }

    public void deleteTV(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Телевизор не найден");
        repository.deleteById(id);
    }

    public List<TV> searchByPriceGreaterThan(Double price) {
        return repository.findByPriceGreaterThan(price);
    }
}