package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.Teeth;
import org.example.test_orm.repository.TeethRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeethService {
    private final TeethRepository teethRepository;


    public List<Teeth> getTeeth() {
        return teethRepository.findAll();
    }
}
