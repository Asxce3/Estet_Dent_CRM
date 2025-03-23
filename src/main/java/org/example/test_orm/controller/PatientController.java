package org.example.test_orm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/patients")
@Slf4j
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String getAllPatients(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        model.addAttribute("patients", patientService.getPatients(cookies));
        return "patients";
    }

    @GetMapping("/{id}")
    public String getPatient(Model model, @PathVariable long id) {
        model.addAttribute("patient", patientService.getPatient(id));
        return "patient";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Patient> getPatientForSearchResult(Model model, @RequestParam String name) {
        log.info("имя : {}", name);
        List<Patient> patients = patientService.getPatientForInputName(name);
        patients.forEach(x -> log.info("Пациент: {}", x));
        model.addAttribute("patients", patients);
        return patients;
    }

    @GetMapping("/create")
    public String createPatientPage(Model model) {
        model.addAttribute("patient", new Patient());
        return "create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Patient patient, Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        patientService.createPatient(patient, cookies);
        model.addAttribute("patient", patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")     //(TODO) В будущем заменить на delete / 23.03 возникает ошибка
    public String delete(@PathVariable long id, HttpServletRequest request) {
        System.out.println(request.getMethod());
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

}
