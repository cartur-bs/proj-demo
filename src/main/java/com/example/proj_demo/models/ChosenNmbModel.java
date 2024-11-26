package com.example.proj_demo.models;

import com.example.proj_demo.DTOs.ChosenNmbDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chosen_numbers")
public class ChosenNmbModel {
    //Essa classe define os números sorteados
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private List<Integer> chosenNumbers;
    private LocalDateTime choosingTime;
    public ChosenNmbModel() {
    }

    public ChosenNmbModel(ChosenNmbDTO chosenNmbDTO, LocalDateTime choosingTime) {
        this.chosenNumbers = chosenNmbDTO.chosenNumbers();
        this.choosingTime = choosingTime;
    }

    public UUID getId() {
        return id;
    }

    public List<Integer> getChosenNumbers() {
        return chosenNumbers;
    }

    public LocalDateTime getChoosingTime() {
        return choosingTime;
    }

    @Override
    public String toString() {
        return "chosenNmbModel{" +
                "chosenNumbers=" + chosenNumbers +
                ", choosingTime=" + choosingTime +
                '}';
    }

    public UUID getNumbersModelId() {
        NumbersModel numbersModel = new NumbersModel();
        return numbersModel.getId();
    }
}
