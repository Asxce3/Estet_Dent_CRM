package org.example.test_orm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.test_orm.entity.MedCard;
import org.example.test_orm.entity.Directory;
import org.example.test_orm.service.MedCardService;
import org.example.test_orm.service.test.DirectoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/med_card")
@RequiredArgsConstructor
public class MedCardController {    // TODO расмотреть варианты перенос логики в VisitsController
    private final MedCardService medCardService;
    private final DirectoryService directoryService;

    @GetMapping("/{id}")
    public String getCreatePage(@PathVariable long id, Model model) {
//        model.addAttribute("three", directoryService.getThree());
        model.addAttribute("med_card", medCardService.getNewMedCard(id));
        return "med_card";
    }

    @GetMapping("/directory")
    @ResponseBody
    public List<Directory> getDirectory() {
        return directoryService.getThree();
    }


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
