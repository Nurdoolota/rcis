package com.spring.service;

import com.spring.entity.TV;
import com.spring.repository.TVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TVService {
    private final TVRepository repository;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public TVService(TVRepository repository, JmsTemplate jmsTemplate) {
        this.repository = repository;
        this.jmsTemplate = jmsTemplate;
    }

    public TV addTV(TV tv) {
        if (tv.getPrice() <= 0) throw new IllegalArgumentException("Цена должна быть >0");
        TV savedTV = repository.save(tv);
        jmsTemplate.convertAndSend("adminQueue", "Добавлен телевизор ID: " + savedTV.getId() + ", модель: " + savedTV.getModel());
        return savedTV;
    }

    public TV updateTV(Long id, TV updatedTV) {
        return repository.findById(id).map(tv -> {
            tv.setManufacturer(updatedTV.getManufacturer());
            tv.setModel(updatedTV.getModel());
            tv.setScreenType(updatedTV.getScreenType());
            tv.setPrice(updatedTV.getPrice());
            tv.setQuantity(updatedTV.getQuantity());
            TV savedTV = repository.save(tv);
            jmsTemplate.convertAndSend("adminQueue", "Обновлён телевизор ID: " + id + ", новая модель: " + savedTV.getModel());
            return savedTV;
        }).orElseThrow(() -> new IllegalArgumentException("Телевизор не найден"));
    }

    public void deleteTV(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Телевизор не найден");
        repository.deleteById(id);
        jmsTemplate.convertAndSend("adminQueue", "Удалён телевизор ID: " + id);
    }

    public List<TV> getAllTV() {
        return repository.findAll();
    }

    public Optional<TV> getTVById(Long id) {
        return repository.findById(id);
    }

    public List<TV> searchByPriceGreaterThan(Double price) {
        return repository.findByPriceGreaterThan(price);
    }
}