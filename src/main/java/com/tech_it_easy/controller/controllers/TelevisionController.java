package com.tech_it_easy.controller.controllers;

import com.tech_it_easy.controller.exceptions.NameTooLongException;
import com.tech_it_easy.controller.exceptions.RecordNotFoundException;
import com.tech_it_easy.controller.models.Television;
import com.tech_it_easy.controller.repositories.TelevisionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {
    private final TelevisionRepository tvRepository;

    public TelevisionController(TelevisionRepository tvRepository) {
        this.tvRepository = tvRepository;
    }

    @PostMapping()
    public ResponseEntity<Television> addTelevision(@RequestBody Television television) {
        if (television.getName().length() > 20) {
            throw new NameTooLongException("Television name exceeds 20 characters.");
        }
        this.tvRepository.save(television);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + television.getId()).toUriString());
        return ResponseEntity.created(uri).body(television);
    }

    @GetMapping()
    public ResponseEntity<List<Television>> getTelevision(@RequestParam(required = false) String brand) {
        if (brand != null) {
            List<Television> filteredTelevisions = tvRepository.findByBrandIgnoreCase(brand);
            return ResponseEntity.ok(filteredTelevisions);
        }
        List<Television> allTelevisions = tvRepository.findAll();
        return ResponseEntity.ok(allTelevisions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Television> getTelevisionById(@PathVariable Long id) {
        Optional<Television> television = this.tvRepository.findById(id);
        if (television.isPresent()) {
            return ResponseEntity.ok(television.get());
        } else {
            throw new RecordNotFoundException("Television with ID " + id + " not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Television> updateTelevision(@PathVariable Long id, @RequestBody Television television) {
        if (television == null || television.getName() == null || television.getName().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        if (television.getName().length() > 20) {
            throw new NameTooLongException("Television name exceeds 20 characters.");
        }

        Optional<Television> existingTelevision = this.tvRepository.findById(id);
        if (existingTelevision.isPresent()) {
            television.setId(existingTelevision.get().getId());
            this.tvRepository.save(television);
            return ResponseEntity.ok(television);
        }
        throw new RecordNotFoundException("Television with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelevision(@PathVariable Long id) {
        if (!tvRepository.existsById(id)) {
            throw new RecordNotFoundException("Television with ID " + id + " not found.");
        }
        tvRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}