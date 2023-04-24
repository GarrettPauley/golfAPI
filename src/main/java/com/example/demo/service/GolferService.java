package com.example.demo.service;

import com.example.demo.domain.Golfer;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.GolferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GolferService {

    private GolferRepository repository;

    @Autowired
    public GolferService(GolferRepository _repository){
        this.repository = _repository;
    }

    public Golfer getGolferById(int id) throws UserNotFoundException{
        return repository.findById(id).orElseThrow( () -> new UserNotFoundException(id));
    }

    public List<Golfer> getGolfers(){
        return repository.findAll();
    }
    public Golfer saveGolfer (Golfer golfer){
        return repository.save(golfer);
    }

    public void deleteGolfer(int id ){
        repository.deleteById(id);

    }

}
