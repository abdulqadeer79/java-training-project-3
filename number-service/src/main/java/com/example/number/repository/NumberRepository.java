package com.example.number.repository;

import com.example.number.dto.SquaredDto;
import com.example.number.model.SimpleNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends JpaRepository<SimpleNumber, Integer> {
}
