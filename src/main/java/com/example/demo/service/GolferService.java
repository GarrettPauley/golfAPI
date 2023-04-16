package com.example.demo.service;

import com.example.demo.domain.Golfer;
import com.example.demo.repositories.GolferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GolferService {

    private GolferRepository repository;

    @Autowired
    public GolferService(GolferRepository _repository){
        this.repository = _repository;
    }

    private Optional<Golfer> getGolferById(int id){
        return repository.findById(id);
    }

    private Iterable<Golfer> getGolfers(){
        return repository.findAll();
    }
}
