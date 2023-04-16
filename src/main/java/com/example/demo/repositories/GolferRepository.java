package com.example.demo.repositories;

import com.example.demo.domain.Golfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GolferRepository extends CrudRepository<Golfer, Integer> {
}
