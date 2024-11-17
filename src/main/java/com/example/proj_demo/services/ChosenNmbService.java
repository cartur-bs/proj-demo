package com.example.proj_demo.services;

import com.example.proj_demo.models.ChosenNmbModel;
import com.example.proj_demo.repository.ChosenNmbRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChosenNmbService {

    @Autowired
    private ChosenNmbRepository chosenNmbRepository;

    public LocalDateTime getTime(){
        return LocalDateTime.now();
    }

    public boolean validateListLength(List<Integer> numbers){
        return numbers.size() == 10;
    }

    public Map<UUID, Integer> compararNúmeros(List<Integer> chosenNumbers) {
        List<ChosenNmbModel> chosenNmbList = chosenNmbRepository.findAll();
        Map<UUID, Integer> resultados = new HashMap<>();

        for (ChosenNmbModel chosenNmb : chosenNmbList) {
            List<Integer> numerosEscolhidos = chosenNmb.getChosenNumbers();
            long iguais = numerosEscolhidos.stream()
                    .filter(chosenNumbers::contains)
                    .count();

            if (iguais > 0) {
                resultados.put(chosenNmb.getId(), (int) iguais);
            }
        }
        return resultados;
    }
}

