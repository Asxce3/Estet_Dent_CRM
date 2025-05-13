package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.DTO.teeth.TeethDTO;
import org.example.test_orm.DTO.teeth.ToothConditionDTO;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.entity.Teeth;
import org.example.test_orm.entity.ToothCondition;
import org.example.test_orm.repository.teeth.TeethRepository;
import org.example.test_orm.repository.teeth.ToothConditionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeethService {
    private final TeethRepository teethRepository;
    private final ToothConditionRepository toothConditionRepository;

    public List<TeethDTO> getTeeth(long patientId) {
        List<TeethDTO> teethDTOList = new ArrayList<>();

        for(Teeth teeth : teethRepository.getTeethByPatientID(patientId)) {
            teethDTOList.add(teethMappingDTO(teeth));
        }
        return teethDTOList;
    }

    public List<ToothConditionDTO> getToothCondition() {
        List<ToothConditionDTO> conditionDTOList = new ArrayList<>();

        for(ToothCondition condition : toothConditionRepository.findAll()) {
            conditionDTOList.add(toothConditionMappingDTO(condition));
        }
        return conditionDTOList;
    }

    public Patient createPatientTeeth(Patient patient) {
        ToothCondition healthCondition = toothConditionRepository.findById(1L).orElseThrow();
        for(int i = 1; i < 5; i++) {
            for(int j = 1; j < 9; j++) {
                Teeth teeth = new Teeth();
                teeth.setQuadrant(i);
                teeth.setNumber(j);
                teeth.setToothCondition(healthCondition);
                patient.addTeeth(teeth);
            }
        }
        return patient;
    }

    private ToothCondition getToothCondition(String name) {
        Optional<ToothCondition> optionalToothCondition = toothConditionRepository.getToothConditionByCondition(name);
        if(optionalToothCondition.isEmpty()) {
            throw new RuntimeException("This toothCondition not exist");
        }
        return optionalToothCondition.get();
    }

    private ToothConditionDTO toothConditionMappingDTO(ToothCondition toothCondition) {
        ToothConditionDTO conditionDTO = new ToothConditionDTO();
        conditionDTO.setId(toothCondition.getId());
        conditionDTO.setCondition(toothCondition.getCondition());
        conditionDTO.setSymbol(toothCondition.getSymbol());
        return conditionDTO;
    }

    private TeethDTO teethMappingDTO(Teeth teeth) {
        TeethDTO teethDTO = new TeethDTO();
        teethDTO.setId(teeth.getId());
        teethDTO.setPatientId(teeth.getPatient().getID());
        teethDTO.setToothConditionId(teeth.getToothCondition().getId());
        teethDTO.setNumber(teeth.getNumber());
        teethDTO.setQuadrant(teeth.getQuadrant());
        return teethDTO;
    }

    private Teeth DTOMappingTeeth(TeethDTO dto) {
        Teeth teeth = new Teeth();
        teeth.setId(dto.getId());
        teeth.setNumber(dto.getNumber());
        teeth.setQuadrant(dto.getQuadrant());

        return teeth;
    }

    private ToothCondition DTOMappingtoothCondition(ToothConditionDTO dto) {
        ToothCondition toothCondition = new ToothCondition();
        toothCondition.setId(dto.getId());
        toothCondition.setCondition(dto.getCondition());
        toothCondition.setSymbol(dto.getSymbol());
        return toothCondition;
    }

    public Patient updatePatientTeeth(Patient patient, List<TeethDTO> patientTeeth) {
        List<ToothCondition> teethCondition = toothConditionRepository.findAll();

        for(TeethDTO dto: patientTeeth) {
            Long id = dto.getToothConditionId();
            ToothCondition toothCondition = teethCondition.stream().filter(t-> t.getId().equals(id)).findAny().orElseThrow();
            Teeth teeth = patient.getTeethList().stream().filter(i -> i.getId() == dto.getId()).findAny().orElseThrow();
            teeth.setToothCondition(toothCondition);
        }
        return patient;
    }


}
