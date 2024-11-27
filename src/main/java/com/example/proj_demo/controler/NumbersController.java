package com.example.proj_demo.controler;

import com.example.proj_demo.DTOs.NumbersDTO;
import com.example.proj_demo.models.NumbersModel;
import com.example.proj_demo.repository.NumbersRepository;
import com.example.proj_demo.services.NumbersService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.layout.Document;
import java.util.stream.Collectors;


import java.io.ByteArrayOutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/numbers")
public class NumbersController {

    @Autowired
    NumbersRepository numbersRepository;

    @Autowired
    NumbersService numbersService;

    @PostMapping("/post")
    public ResponseEntity<?> putNumbers(@RequestBody NumbersDTO numbersDTO) {
        try {
            NumbersModel numbersModel = new NumbersModel(numbersDTO, numbersService.getTime());

            if (!numbersService.validateListLength(numbersDTO.numbers())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INSIRA 10 DEZENAS");
            }
            if (!numbersService.notRepeatedNumbers(numbersDTO.numbers())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NÃO REPITA DEZENAS");
            }

            // Criar o PDF e definir o conteúdo no modelo
            byte[] pdfContent = numbersService.createPdf(numbersModel);
            numbersModel.setPdfContent(pdfContent);

            numbersRepository.save(numbersModel);
            System.out.println(numbersModel);
            return ResponseEntity.ok().body("Novo cadastro feito " + numbersModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("HOUVE UM ERRO AO SALVAR O DADO, TENTE NOVAMENTE");
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable UUID id) {
        try {
            // Buscar o modelo pelo ID
            NumbersModel numbersModel = numbersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("PDF não encontrado"));

            // Criar o documento PDF
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);  // Criando o PdfWriter
            PdfDocument pdfDocument = new PdfDocument(writer);  // Criando o PdfDocument a partir do PdfWriter
            Document document = new Document(pdfDocument);  // Criando o Document a partir do PdfDocument

            // Adicionar ID
            document.add(new Paragraph("Código: " + numbersModel.getId().toString()));

            String numbersList = numbersModel.getNumbers().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            // Adicionar os números ao PDF
            document.add(new Paragraph("Números comprados: " + numbersList));

            String phoneNumber = numbersModel.getPhoneNumber();
            document.add(new Paragraph("Número de telefone: " + phoneNumber));

            // Fechar o documento
            document.close();

            // Obter o conteúdo do PDF gerado
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
            System.out.println(e.getMessage());
            System.out.println("-------");
            System.out.println(e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
