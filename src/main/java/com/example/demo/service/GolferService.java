package com.example.demo.service;

import com.example.demo.domain.Golfer;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.GolferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class GolferService {

    private GolferRepository repository;

    @Autowired
    public GolferService(GolferRepository _repository){
        this.repository = _repository;
    }

    public Optional<Golfer> getGolferById(int id) throws UserNotFoundException{
        return Optional.of(repository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }
    public Optional<Golfer> getGolferByName(String name) throws UserNotFoundException{
        return Optional.of(repository.findByName(name).orElseThrow(() -> new UserNotFoundException(name)));
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
        repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        repository.deleteById(id);
    }



}
