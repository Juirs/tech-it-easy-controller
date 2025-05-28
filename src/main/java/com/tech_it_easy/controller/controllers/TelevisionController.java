package com.tech_it_easy.controller.controllers;

import com.tech_it_easy.controller.exceptions.NameTooLongException;
import com.tech_it_easy.controller.exceptions.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {
    private final List<String> database;

    public TelevisionController() {
        this.database = new ArrayList<>();
    }

    @GetMapping()
    public ResponseEntity<List<String>> getTelevision(@RequestParam(required = false) String brand) {
        if(brand != null) {
            List<String> filteredTelevisions = new ArrayList<>();
            for (String television : database) {
                if (television.toLowerCase().contains(brand.toLowerCase())) {
                    filteredTelevisions.add(television);
                }
            }
            return ResponseEntity.ok(filteredTelevisions);
        }
        return ResponseEntity.ok(database);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTelevisionById(@PathVariable int id) {
        int zeroBasedId = id - 1;
        if (zeroBasedId < 0 || zeroBasedId >= database.size()) {
            throw new RecordNotFoundException("Television with ID " + id + " not found.");
        }
        return ResponseEntity.ok(database.get(zeroBasedId));
    }

    @PostMapping()
    public ResponseEntity<String> addTelevision(@RequestBody String television) {
        if (television.length() > 20) {
            throw new NameTooLongException("Television name exceeds 20 characters.");
        }
        database.add(television);
        int newId = database.size();
        String location = String.format("/televisions/%d", newId);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTelevision(@PathVariable int id, @RequestBody String television) {
        if (television == null || television.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        int zeroBasedId = id - 1;
        if (zeroBasedId < 0 || zeroBasedId >= database.size()) {
            throw new RecordNotFoundException("Television with ID " + id + " not found.");
        }
        if (television.length() > 20) {
            throw new NameTooLongException("Television name exceeds 20 characters.");
        }
        database.set(zeroBasedId, television);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTelevision(@PathVariable int id) {
        int zeroBasedId = id - 1;
        if (zeroBasedId < 0 || zeroBasedId >= database.size()) {
            throw new RecordNotFoundException("Television with ID " + id + " not found.");
        }
        database.remove(zeroBasedId);
        return ResponseEntity.ok().build();
    }
}
