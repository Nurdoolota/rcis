package com.spring.controller;

import com.spring.entity.TV;
import com.spring.service.TVService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tvs")
public class TVRestController {

    private final TVService tvService;

    @Autowired
    public TVRestController(TVService tvService) {
        this.tvService = tvService;
    }

    @GetMapping
    public ResponseEntity<List<TV>> getAllTVs() {
        return ResponseEntity.ok(tvService.getAllTV());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TV> getTVById(@PathVariable Long id) {
        Optional<TV> tv = tvService.getTVById(id);
        return tv.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TV> addTV(@Valid @RequestBody TV tv) {
        TV savedTV = tvService.addTV(tv);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTV);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TV> updateTV(@PathVariable Long id, @Valid @RequestBody TV updatedTV) {
        try {
            TV tv = tvService.updateTV(id, updatedTV);
            return ResponseEntity.ok(tv);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTV(@PathVariable Long id) {
        try {
            tvService.deleteTV(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}