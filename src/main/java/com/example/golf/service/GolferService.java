package com.example.golf.service;

import com.example.golf.domain.Golfer;
import com.example.golf.exceptions.GolferNotFoundException;
import com.example.golf.repositories.GolferRepository;
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

    public Optional<Golfer> getGolferById(int id) throws GolferNotFoundException{
        return Optional.of(repository.findById(id).orElseThrow(() -> new GolferNotFoundException(id)));
    }
    public Optional<Golfer> getGolferByName(String name) throws GolferNotFoundException{
        return Optional.of(repository.findByName(name).orElseThrow(() -> new GolferNotFoundException(name)));
    }


    public List<Golfer> getGolfers(){
        return repository.findAll();
    }
    public Golfer saveGolfer (Golfer golfer){
        return repository.save(golfer);
    }
    public Golfer updateGolfer(Golfer golfer){
        return repository.save(golfer);
    }

    public void deleteGolfer(int id ) {
        repository.findById(id).orElseThrow(() -> new GolferNotFoundException(id));
        repository.deleteById(id);
    }



}
