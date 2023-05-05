package com.example.golf.controllers;

import com.example.golf.domain.Golfer;
import com.example.golf.exceptions.GolferNotFoundException;
import com.example.golf.service.GolferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GolferController {
    @Autowired
    private GolferService service;


    @GetMapping("/golfers")
    @ResponseBody
    private Iterable<Golfer> helloGolfer(){
        return service.getGolfers();

    }

    @GetMapping("/golfers/{id}")
    private Optional<Golfer> getGolfer(@PathVariable int id) throws GolferNotFoundException{
        return service.getGolferById(id);
    }


    @PostMapping("/golfers")
    private ResponseEntity<Golfer> addGolfer(@Valid @RequestBody Golfer golfer) {
        service.saveGolfer(golfer);
        return new ResponseEntity(golfer, HttpStatus.CREATED);
    }

    @PutMapping("/golfers/{id}")
    private ResponseEntity<Golfer> updateGolfer(@Valid @RequestBody Golfer golfer){
        service.saveGolfer(golfer);
        return new ResponseEntity(golfer, HttpStatus.OK);
    }


    @DeleteMapping("/golfers/{id}")
    private void deleteGolfer(@PathVariable int id) throws GolferNotFoundException {
        service.deleteGolfer(id);
    }

}
