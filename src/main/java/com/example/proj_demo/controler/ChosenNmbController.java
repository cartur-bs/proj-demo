package com.example.proj_demo.controler;

import com.example.proj_demo.DTOs.ChosenNmbDTO;
import com.example.proj_demo.models.ChosenNmbModel;
import com.example.proj_demo.models.NumbersModel;
import com.example.proj_demo.repository.ChosenNmbRepository;
import com.example.proj_demo.repository.NumbersRepository;
import com.example.proj_demo.services.ChosenNmbService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chosen")
public class ChosenNmbController {

    @Autowired
    ChosenNmbRepository chosenNmbRepository;
    @Autowired
    ChosenNmbService chosenNmbService;
    @Autowired
    NumbersRepository numbersRepository;

    //persiste um novo resultado
    @PostMapping("/post")
    public ResponseEntity<?> postChosen(@RequestBody ChosenNmbDTO chosenNmbDTO, ChosenNmbService chosenNmbService) throws Exception {
        try {
            ChosenNmbModel chosenNmbModel = new ChosenNmbModel(chosenNmbDTO, chosenNmbService.getTime());
            if (!chosenNmbService.validateListLength(chosenNmbDTO.chosenNumbers())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("too big");
            }
            chosenNmbRepository.save(chosenNmbModel);
            return ResponseEntity.ok().body("novo resultado salvo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("o erro foi " + e);
        }

    }

    //compara o resultado com os números vendidos
    @PostMapping("/compare")
    public Map<UUID, Integer> compararNúmeros(@RequestBody ChosenNmbDTO chosenNmbDTO) {
        return chosenNmbService.compararNúmeros(chosenNmbDTO.chosenNumbers());
    }

    //baixa o pdf com as compras com numeros iguais - deve ser rodada no navegador
    @GetMapping("/download/{numbers}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String numbers) {
        try {
            // Converter a string de números para uma lista de inteiros
            List<Integer> numberList = Arrays.stream(numbers.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            System.out.println("Números recebidos na requisição: " + numberList);

            // Chama o método de comparação
            Map<UUID, Integer> resultados = chosenNmbService.compararNúmeros(numberList);

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum número correspondente encontrado.".getBytes());
            }

            // Ordena os resultados em ordem decrescente de quantidade de números iguais
            List<Map.Entry<UUID, Integer>> sortedResults = new ArrayList<>(resultados.entrySet());
            sortedResults.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Criar o PDF
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Adicionar os resultados ao PDF
            for (Map.Entry<UUID, Integer> entry : sortedResults) {
                UUID id = entry.getKey();
                Integer quantidadeIguais = entry.getValue();

                Optional<NumbersModel> numbersModelOptional = numbersRepository.findById(id);
                if (numbersModelOptional.isPresent()) {
                    NumbersModel numbersModel = numbersModelOptional.get();
                    String phoneNumber = numbersModel.getPhoneNumber();

                    // Adicionando os dados ao PDF
                    document.add(new Paragraph("ID: " + id));
                    document.add(new Paragraph("Quantidade de números iguais: " + quantidadeIguais));
                    document.add(new Paragraph("Número de telefone: " + phoneNumber));
                    document.add(new Paragraph("--------------------------------------------------"));
                }
            }

            // Fechar o documento PDF
            document.close();
            byte[] pdfContent = byteArrayOutputStream.toByteArray();

            // Definir os cabeçalhos para o retorno do PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "numbers_report.pdf");
            headers.setContentLength(pdfContent.length);

            // Retornar a resposta com o PDF gerado
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfContent); // O conteúdo binário do PDF gerado

        } catch (Exception e) {
            e.printStackTrace();  // Adicionando stacktrace para debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Erro ao gerar o PDF: " + e.getMessage()).getBytes());
        }
    }
}
