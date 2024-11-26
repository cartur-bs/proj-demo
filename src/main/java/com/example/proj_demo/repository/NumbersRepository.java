package com.example.proj_demo.repository;

import com.example.proj_demo.models.NumbersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NumbersRepository extends JpaRepository<NumbersModel, UUID> {
}
