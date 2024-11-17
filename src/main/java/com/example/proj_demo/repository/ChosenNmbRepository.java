package com.example.proj_demo.repository;

import com.example.proj_demo.models.ChosenNmbModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChosenNmbRepository extends JpaRepository<ChosenNmbModel, UUID> {

}
