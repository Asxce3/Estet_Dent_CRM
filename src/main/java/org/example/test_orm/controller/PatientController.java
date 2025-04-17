package org.example.test_orm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.MedCard;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.service.MedCardService;
import org.example.test_orm.service.MedHistoryService;
import org.example.test_orm.service.PatientService;
import org.example.test_orm.service.VisitsService;
import org.example.test_orm.service.auth.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;


@Controller
@RequestMapping("/patients")
@Slf4j
@RequiredArgsConstructor
public class PatientController {
    private final AuthService authService;
    private final PatientService patientService;
    private final MedHistoryService medHistoryService;
    private final VisitsService visitsService;
    private final MedCardService medCardService;

    @GetMapping
    public String getAllPatients(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        model.addAttribute("patients", patientService.getPatients(cookies));
        return "patients";
    }

    @GetMapping("/{id}")
    public String getPatient(Model model, @PathVariable long id) {
        model.addAttribute("med_cards", medCardService.getMedCardsByPatientID(id));
        model.addAttribute("visits", visitsService.getPatientVisits(id));
        model.addAttribute("med_histories", medHistoryService.getMedHistories(id));
        model.addAttribute("patient", patientService.getPatient(id));
        return "patient";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Patient> getPatientForSearchResult(Model model, @RequestParam String name, HttpServletRequest request) {
        log.info("имя : {}", name);
        Doctor doctor = authService.getDoctorFromCookie(request.getCookies());
        List<Patient> patients = patientService.getPatientForInputName(name, doctor);
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