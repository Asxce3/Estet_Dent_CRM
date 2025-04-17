package org.example.test_orm.controller;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.DTO.MedHistoryDTO;
import org.example.test_orm.entity.MedHistory;
import org.example.test_orm.service.MedHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class MedHistoryController {
    private final MedHistoryService medHistoryService;

    @GetMapping("/{patientId}")
    @ResponseBody
    public List<MedHistory> getMedHistories(@PathVariable long patientId) {
        return medHistoryService.getMedHistories(patientId);
    }

    @GetMapping("/create/{patientId}")
    public String createPageMedHistory(@PathVariable long patientId, Model model) {
        MedHistoryDTO medHistoryDTO = new MedHistoryDTO();
        medHistoryDTO.setPatientId(patientId);
        model.addAttribute("med_history", medHistoryDTO);
        return "history/create";
    }

    // TODO Обновление и удаление отложить на какое-то время (Самвел)
//    @GetMapping("/update")
//    public String updatePageMedHistory(Model model) {
//    }
//
//    @GetMapping("/delete")
//    public String deletePageMedHistory(Model model) {
//    }

    @PostMapping("/create")
    public String create(@ModelAttribute MedHistoryDTO medHistoryDTO) {
        System.out.println(medHistoryDTO);
        medHistoryService.create(medHistoryDTO);
        return "redirect:/patients";    //TODO временная переадресация
    }
}
