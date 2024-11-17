package com.example.proj_demo.services;

import com.example.proj_demo.models.NumbersModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    public byte[] createPdf(NumbersModel numbersModel) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(25, 700);
            contentStream.showText("Numbers: " + numbersModel.getNumbers());
            contentStream.endText();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return baos.toByteArray();
    }

}
