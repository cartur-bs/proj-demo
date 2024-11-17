package com.example.proj_demo.controler;

import com.example.proj_demo.DTOs.ChosenNmbDTO;
import com.example.proj_demo.models.ChosenNmbModel;
import com.example.proj_demo.repository.ChosenNmbRepository;
import com.example.proj_demo.services.ChosenNmbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chosen")
public class ChosenNmbController {

    @Autowired
    ChosenNmbRepository chosenNmbRepository;

    @Autowired
    ChosenNmbService chosenNmbService;

    @PostMapping("/post")
    public ResponseEntity<?> postChosen(@RequestBody ChosenNmbDTO chosenNmbDTO, ChosenNmbService chosenNmbService ) throws Exception{
       try{
           ChosenNmbModel chosenNmbModel = new ChosenNmbModel(chosenNmbDTO, chosenNmbService.getTime());
           if(!chosenNmbService.validateListLength(chosenNmbDTO.chosenNumbers())){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("too big");
           }
           chosenNmbRepository.save(chosenNmbModel);
           return ResponseEntity.ok().body("novo resultado salvo");
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("o erro foi " + e);
       }

    }

    @PostMapping("/compare")
    public Map<UUID, Integer> compararNúmeros(@RequestBody ChosenNmbDTO chosenNmbDTO) {
        return chosenNmbService.compararNúmeros(chosenNmbDTO.chosenNumbers());
    }
}
