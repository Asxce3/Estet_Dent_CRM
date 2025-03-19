package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.repository.VisitsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitsService {
    private final VisitsRepository visitsRepository;

    public void createVisits(Visits visits) {
        visitsRepository.save(visits);
    }
}
