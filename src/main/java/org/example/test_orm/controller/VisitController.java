package org.example.test_orm.controller;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.service.VisitsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitsService visitsService;
    @GetMapping
    public String createPageVisit() {
        return "create_visit";
    }

    @PostMapping
    public void createTime(@ModelAttribute Visits visits) {
        visitsService.createVisits(visits);
    }

//    @PostMapping
//    public void createPageVisit() {
//        return "create_visit";
//    }


}
