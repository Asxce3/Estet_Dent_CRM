package org.example.test_orm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.test_orm.DTO.DirectoryDTO;
import org.example.test_orm.DTO.DirectoryValueDTO;
import org.example.test_orm.entity.MedCard;
import org.example.test_orm.service.MedCardService;
import org.example.test_orm.service.DirectoryService;
import org.example.test_orm.service.TeethService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/med_card")
@RequiredArgsConstructor
public class MedCardController {    // TODO расмотреть варианты перенос логики в VisitsController
    private final MedCardService medCardService;
    private final DirectoryService directoryService;
    private final TeethService teethService;


    @GetMapping("/{id}")
    public String getCreatePage(@PathVariable long id, Model model) {
        model.addAttribute("med_card", medCardService.getNewMedCard(id));
        return "med_card";
    }

    @GetMapping("/directories")
    @ResponseBody
    public List<DirectoryDTO> getDirectory() {
        return directoryService.getThree();
    }

    @PostMapping("/directories")
    @ResponseBody
    public ResponseEntity<?> createDirectory(@RequestBody @Valid DirectoryDTO directory, BindingResult bindingResult) {
        log.info("Request for creating directory {}", directory);
        if(bindingResult.hasErrors()) {
            log.error("An error occurred when creating the Directory object");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DirectoryDTO dto = directoryService.createDirectory(directory);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/directories/{id}")
    @ResponseBody
    public ResponseEntity<?> updateDirectory(@PathVariable long id, @RequestBody @Valid DirectoryDTO directory, BindingResult bindingResult) {
        log.info("Request for updating directory with id {}\n directory : {}", id, directory);
        if(bindingResult.hasErrors()) {
            log.error("An error occurred when updating the Directory object");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        directoryService.updateDirectory(directory);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/directories/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteDirectory(@PathVariable long id) {
        log.info("Request for deleting directory with id {}", id);
        directoryService.deleteDirectory(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping("/directories/values")
    @ResponseBody
    public ResponseEntity<?> createDirectoryValue(@RequestBody @Valid DirectoryValueDTO value, BindingResult bindingResult) {
        log.info("Request for create value with directory : {}", value);
        if(bindingResult.hasErrors()) {
            log.error("An error occurred when creating the DirectoryValueDTO object");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DirectoryValueDTO dto = directoryService.createDirectoryValue(value);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PutMapping("/directories/values/{id}")
    @ResponseBody
    public ResponseEntity<?> updateDirectoryValue(@PathVariable long id,
                                     @RequestBody @Valid DirectoryValueDTO value, BindingResult bindingResult) {
        log.info("Request for update value with directoryId {}\n directory : {}", id, value);
        if(bindingResult.hasErrors()) {
            log.error("An error occurred when updating the DirectoryValueDTO object");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        directoryService.updateDirectoryValue(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/directories/values/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteDirectoryValue(@PathVariable long id) {
        log.info("Request for deleting value with id: {}", id);
        directoryService.deleteDirectoryValue(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @GetMapping("/teeth")
//    @ResponseBody
//    public List<Teeth> getTeeth() {
//        return teethService.getTeeth();
//    }


//    @PostMapping("/document/upload")    // TODO (Самвел) временно
//    public String CreateUploadDocumentPage(@RequestParam("files[]") List<MultipartFile> files, @ModelAttribute MedCard medCard) {
//        String uploadDir = "/Users/samvel/Desktop/test_download/";
//        for (MultipartFile file : files) {
//            try {
//                File destination = new File(uploadDir + file.getOriginalFilename());
//                file.transferTo(destination);
//            } catch (IOException e) {
//                log.info(e.getMessage());
//            }
//        }
//        medCardService.create(medCard);
//        return "redirect:/patients";
//    }

    @GetMapping("/document/{id}")
    public String getDocumentMedCard(@PathVariable long id, Model model) {
        log.info("Запрос на отправку документа ");
        model.addAttribute("med_card", medCardService.getMedCardByID(id));
        return "document/document_med_card";
    }


    @PostMapping
    public String createMedCard(@ModelAttribute MedCard medCard) {
        medCardService.create(medCard);
        return "redirect:/patients"; // TODO (Самвел) в будущем заменить переадрисацию
    }

}
