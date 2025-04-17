package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.Preset;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PresetService {
    private final DiagnoseRepository diagnoseRepository;

    public Preset createPreset() {
        Preset preset = new Preset();
        preset.setDiagnoseList(diagnoseRepository.findAll());
        return preset;
    }
}
