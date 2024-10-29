package com.example.proj_demo.controler;

import com.example.proj_demo.DTOs.NumbersDTO;
import com.example.proj_demo.models.NumbersModel;
import com.example.proj_demo.repository.NumbersRepository;
import com.example.proj_demo.services.NumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/numbers")
public class NumbersController {

    @Autowired
    NumbersRepository numbersRepository;

    @Autowired
    NumbersService numbersService;
    @PostMapping("/post")
    public ResponseEntity<?> putNumbers(@RequestBody NumbersDTO numbersDTO, NumbersService numbersService) throws Exception {
        try{
            NumbersModel numbersModel = new NumbersModel(numbersDTO, numbersService.getTime());
            if(!numbersService.validateListLength(numbersDTO.numbers())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INSIRA 10 DEZENAS");
            }
            if(!numbersService.notRepeatedNumbers(numbersDTO.numbers())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NÃO REPITA DEZENAS");
            }
            numbersRepository.save(numbersModel);
            System.out.println(numbersModel);
            return ResponseEntity.ok().body("novo cadastro feito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("HOUVE UM ERRO AO SALVAR O DADO, TENTE NOVAMENTE");
        }

    }
}
