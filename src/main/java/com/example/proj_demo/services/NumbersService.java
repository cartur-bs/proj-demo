package com.example.proj_demo.services;

import com.example.proj_demo.DTOs.NumbersDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class NumbersService {
    public boolean validateListLength(List<Integer> numbers){
        if(numbers.size() != 10){
            return false;
        }else return true;
    }

    public boolean notRepeatedNumbers(List<Integer> numbers) {
        // Cria um Set para armazenar os números
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);

        // Verifica se o tamanho do Set é igual ao da lista original
        return uniqueNumbers.size() == numbers.size();
    }

    public LocalDateTime getTime(){
        LocalDateTime purchaseTime = LocalDateTime.now();
        return purchaseTime;
    }

}
