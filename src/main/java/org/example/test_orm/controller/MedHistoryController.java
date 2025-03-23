package org.example.test_orm.controller;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.MedHistory;
import org.example.test_orm.service.MedHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class MedHistoryController {
    private final MedHistoryService medHistoryService;

    @GetMapping
    @ResponseBody
    public List<MedHistory> getMedHistory(@RequestParam long id) {
        return medHistoryService.getMedHistory(id);
    }
}
