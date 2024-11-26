package com.example.proj_demo.services;

import com.example.proj_demo.models.NumbersModel;
import com.example.proj_demo.repository.NumbersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChosenNmbService {

    @Autowired
    NumbersRepository numbersRepository;

    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

    public boolean validateListLength(List<Integer> numbers) {
        return numbers.size() == 10;
    }

    public Map<UUID, Integer> compararNúmeros(List<Integer> chosenNumbers) {
        // Buscar todos os registros de NumbersModel no banco
        List<NumbersModel> numbersModelList = numbersRepository.findAll();
        Map<UUID, Integer> resultados = new HashMap<>();

        // Iterar sobre cada NumbersModel
        for (NumbersModel numbersModel : numbersModelList) {
            // Obter a lista de números associados ao NumbersModel
            List<Integer> numerosBanco = numbersModel.getNumbers(); // Supondo que 'getNumbers()' retorne a lista de números do NumbersModel
            // Contar quantos números da lista recebida estão presentes nos números do NumbersModel
            long iguais = numerosBanco.stream()
                    .filter(chosenNumbers::contains) // Verifica se o número de banco está na lista recebida
                    .count();

            // Se houver alguma correspondência, armazenar o resultado
            resultados.put(numbersModel.getId(), (int) iguais); // Armazenar o ID do NumbersModel e a quantidade de números iguais

        }

        // Retornar o mapa com os resultados (ID e quantidade de números iguais)
        return resultados;
    }
}

