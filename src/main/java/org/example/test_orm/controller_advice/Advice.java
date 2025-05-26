package org.example.test_orm.controller_advice;

import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.DTO.VisitsDTO;
import org.example.test_orm.exception.*;
import org.example.test_orm.exception.Visits.CreateVisitException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class Advice {

    @ExceptionHandler(CreateVisitException.class)
    public String errorMessageFromDataBase(CreateVisitException e, Model model) {
        log.warn(e.getClass().toString(), e);
        model.addAttribute("error_message",  e.getMessage());
        model.addAttribute("visit", new VisitsDTO());   // TODO по хорошему редиректить с добавление ошибки а не создать по новой шаблон

        return "create_visit";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String errorMessageFromDataBase(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getClass().toString(), e);
        redirectAttributes.addFlashAttribute("error_message",  messageForDuplicateException(e.getMessage()));
        return "redirect:/patients/create";
    }

    @ExceptionHandler(value = {CreateDataOfBirthPatientException.class, TelephoneNumberException.class})
    public String errorMessageFromCreatePatient(PatientException e,  RedirectAttributes redirectAttributes) {
        log.warn(e.getClass().toString(), e);
        redirectAttributes.addFlashAttribute("error_message", e.getMessage()); // Подумать над этим позже
        return "redirect:/patients/create";
    }

    @ExceptionHandler(value = {PatientNotFoundException.class})
    public String errorMessageFromViewPatient(PatientException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getClass().toString(), e);
        redirectAttributes.addFlashAttribute("error_message", e.getMessage());// Подумать над этим позже
        return "redirect:/patients";
    }

    @ExceptionHandler(DuplicateDoctorException.class)
    public String duplicateDoctor(DuplicateDoctorException e, RedirectAttributes redirectAttributes){
        log.warn(e.getClass().toString(), e);
        redirectAttributes.addFlashAttribute("error_message", messageForDuplicateException(e.getMessage()));
        return "redirect:/register";
    }

    @ExceptionHandler(DoctorSaveException.class)
    public String doctorSave(DoctorSaveException e, RedirectAttributes redirectAttributes){
        log.warn(e.getClass().toString(), e);
        redirectAttributes.addFlashAttribute("error_message", e.getMessage());
        return "redirect:/register";
    }

    private String messageForDuplicateException(String error) {
        int start = error.indexOf("(") + 1;
        int last = error.indexOf(")");
        return "Поле: " + error.substring(start, last) + " уже существует!";
    }

}
