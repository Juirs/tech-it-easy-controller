package com.tech_it_easy.controller.repositories;

import com.tech_it_easy.controller.models.Television;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelevisionRepository extends JpaRepository<Television, Long> {
    List<Television> findByBrandIgnoreCase(String brand);
}