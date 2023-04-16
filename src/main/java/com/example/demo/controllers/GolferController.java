package com.example.demo.controllers;

import com.example.demo.domain.Golfer;
import com.example.demo.service.GolferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GolferController {
    @Autowired
    private GolferService service;


    @GetMapping("/golfers")
    @ResponseBody
    private List<Golfer> helloGolfer(){
        return service.getGolfers()

    }

    @GetMapping("/golfers/{id}")
    private Golfer helloGolfer(@PathVariable int id){
        return null;
    }
}
