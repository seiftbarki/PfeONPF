package com.example.pfe2.service;

import com.example.pfe2.entity.Direction;
import com.example.pfe2.repository.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionService {

    @Autowired
    private DirectionRepository directionRepository;

    public List<Direction> findAll() {
        return directionRepository.findAll();
    }

    public Direction findById(Long id) {
        return directionRepository.findById(id).orElseThrow(() -> new RuntimeException("Direction non trouvée"));
    }

    public Direction save(Direction direction) {
        return directionRepository.save(direction);
    }

    public void delete(Long id) {
        directionRepository.deleteById(id);
    }
}