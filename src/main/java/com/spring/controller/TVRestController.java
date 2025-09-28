package com.spring.controller;

import com.spring.entity.TV;
import com.spring.repository.TVRepository;
import com.spring.service.TVService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tvs")
public class TVRestController {

    private final TVService tvService;


    @Autowired
    public TVRestController(TVService tvService) {
        this.tvService = tvService;
    }

    // GET: Извлечение всех записей (JSON)
    @GetMapping
    public ResponseEntity<List<TV>> getAllTVs() {
        List<TV> tvs = tvService.getAllTV();
        System.out.println("привет" + tvs);
        return ResponseEntity.ok(tvs);
    }

    // GET: Извлечение одной записи по ID (JSON)
    @GetMapping("/{id}")
    public ResponseEntity<TV> getTVById(@PathVariable Long id) {
        return tvService.getTVById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // POST: Добавление записи
    @PostMapping
    public ResponseEntity<TV> addTV(@Valid @RequestBody TV tv) {
        TV savedTV = tvService.addTV(tv);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTV);
    }

    // PUT: Редактирование записи
    @PutMapping("/{id}")
    public ResponseEntity<TV> updateTV(@PathVariable Long id, @Valid @RequestBody TV updatedTV) {
        try {
            TV tv = tvService.updateTV(id, updatedTV);
            return ResponseEntity.ok(tv);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // DELETE: Удаление записи
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTV(@PathVariable Long id) {
        try {
            tvService.deleteTV(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}