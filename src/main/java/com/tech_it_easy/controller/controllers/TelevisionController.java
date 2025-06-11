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
        Television television = tvRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Television with ID " + id + " not found."));
        return ResponseEntity.ok(television);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Television> updateTelevision(@PathVariable Long id, @RequestBody Television television) {
        if (television == null || television.getName() == null || television.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Television existingTelevision = tvRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Television with ID " + id + " not found."));
        if (television.getName().length() > 20) {
            throw new NameTooLongException("Television name exceeds 20 characters.");
        }
        television.setId(existingTelevision.getId());
        tvRepository.save(television);
        return ResponseEntity.ok(television);
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