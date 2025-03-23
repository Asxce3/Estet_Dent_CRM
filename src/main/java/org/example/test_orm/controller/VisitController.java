package org.example.test_orm.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.test_orm.DTO.VisitsDTO;
import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.service.VisitsService;
import org.example.test_orm.service.auth.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitsService visitsService;
    private final AuthService authService;

    @GetMapping
    public String getAllVisits(
            @RequestParam(required = false) String inputWeek, Model model, HttpServletRequest request) {
        Doctor doctor = authService.getDoctorFromCookie(request.getCookies());
        LocalDate date = visitsService.parseOrCreateDate(inputWeek);

        model.addAttribute("daysOfWeek", List.of(DayOfWeek.values()));
        model.addAttribute("startDate", date);
        model.addAttribute("endDate", date.plusDays(6));
        model.addAttribute("visits", visitsService.getVisits(date, doctor));
        return "visits";
    }

    @GetMapping("/create")
    public String createPageVisit(Model model) {
        model.addAttribute("visit", new VisitsDTO());
        return "create_visit";
    }

    @GetMapping("/update/{id}")
    public String updatePageVisit(@PathVariable long id,
                                  Model model) {
        Visits visits = visitsService.getVisit(id);
        model.addAttribute("patient", visits.getPatient());
        model.addAttribute("medHistory", visits.getMedHistory());
        model.addAttribute("visit", visitsService.getVisitDTO(visits));
        return "update_visits";
    }

    @PostMapping
    public String createVisit(@ModelAttribute VisitsDTO visitsDTO) {
        System.out.println("create");
        System.out.println(visitsDTO);
        visitsService.createVisits(visitsDTO);
        return "redirect:/visits";
    }

    @PostMapping("/update")     // (TODO) Самвел - изменить на PUT
    public String updateTimeVisit(@ModelAttribute VisitsDTO visitsDTO) {
        System.out.println("update");
        System.out.println(visitsDTO);
        visitsService.updateVisits(visitsDTO);
        return "redirect:/visits";
    }

    @GetMapping("/delete/{id}")     //(TODO) В будущем заменить на delete / 23.03 возникает ошибка
    public String deleteVisit(@PathVariable long id) {
        visitsService.deleteVisits(id);
        return "redirect:/visits";
    }



}
