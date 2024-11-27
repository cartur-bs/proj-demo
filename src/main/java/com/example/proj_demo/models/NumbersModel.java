package com.example.proj_demo.models;

import com.example.proj_demo.DTOs.NumbersDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Vendas")
public class NumbersModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    private List<Integer> numbers;
    private String phoneNumber;
    private LocalDateTime purchaseTime;
    private byte[] pdfContent;

    public NumbersModel() {
    }

    public NumbersModel(NumbersDTO numbersDTO, LocalDateTime purchaseTime) {
        this.numbers = numbersDTO.numbers();
        this.phoneNumber = numbersDTO.phoneNumber();
        this.purchaseTime = purchaseTime;
    }

    public UUID getId() {
        return id;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public byte[] getPdfContent() {
        return pdfContent;
    }

    public void setPdfContent(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumbersModel that)) return false;
        return Objects.equals(numbers, that.numbers) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers, phoneNumber);
    }

    @Override
    public String toString() {
        return "numbersModel{" +
                "purchaseCode=" + id +
                ", numbers=" + numbers +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
