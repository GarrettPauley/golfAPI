package com.example.golf.repositories;

import com.example.golf.domain.Golfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GolferRepository extends CrudRepository<Golfer, Integer> {
    @Override
    List<Golfer> findAll();
    Optional<Golfer> findByName(String name);
}
