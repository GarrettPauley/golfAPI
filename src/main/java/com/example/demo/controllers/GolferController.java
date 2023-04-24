package com.example.demo.controllers;

import com.example.demo.domain.Golfer;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.GolferService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
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
    private Golfer getGolfer(@PathVariable int id) throws UserNotFoundException {
        return service.getGolferById(id);
    }


    @PostMapping("/golfers")
    private ResponseEntity<Golfer> addGolfer(@Valid @RequestBody Golfer golfer) {
        service.saveGolfer(golfer);
        return new ResponseEntity(golfer, HttpStatus.CREATED);

    }

    @DeleteMapping("/golfers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteGolfer(@PathVariable int id) {
        service.deleteGolfer(id);
    }

}
