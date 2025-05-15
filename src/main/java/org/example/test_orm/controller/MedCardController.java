package org.example.test_orm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.test_orm.DTO.DirectoryDTO;
import org.example.test_orm.DTO.DirectoryValueDTO;
import org.example.test_orm.DTO.DocumentDTO;
import org.example.test_orm.DTO.MedCardDTO;
import org.example.test_orm.entity.Document;
import org.example.test_orm.service.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/med_card")
@RequiredArgsConstructor
public class MedCardController {    // TODO расмотреть варианты перенос логики в VisitsController
    private final MedCardService medCardService;
    private final DirectoryService directoryService;
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public String getCreatePage(@PathVariable long id, Model model) {
        model.addAttribute("med_card", medCardService.getNewMedCard(id));
        return "med_card";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createMedCard(@RequestBody MedCardDTO medCardDTO) {
        log.info("Request for creating medCard : {}", medCardDTO);
        medCardService.create(medCardDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMedCard(@PathVariable long id) {
        log.info("Request for deleting med with id {}", id);
        medCardService.deleteMeCard(id);
        return new ResponseEntity<>(HttpStatus.OK);
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



    @PostMapping("/document/upload")
    @ResponseBody
    public List<Document> CreateUploadDocumentPage(@RequestParam("files") List<MultipartFile> files){
        log.info("Request for uploading file");
        return documentService.createFiles(files);
    }

    @GetMapping("/document/{id}")
    public String printMedCard(@PathVariable long id, Model model) {
        log.info("Запрос на отправку медкарты ");
        model.addAttribute("med_card", medCardService.getMedCardByID(id));
        return "document/document_med_card";
    }

    @GetMapping("/{id}/document/download")
    public ResponseEntity<Resource> getDocuments(@PathVariable long id) {
        log.info("Request for getting med_card file with id  {}", id);
        DocumentDTO documentDTO = documentService.getMedCardFile(id);
        String contentType = "application/octet-stream"; // Тип содержимого
        try {
            contentType = Files.probeContentType(documentDTO.getResource().getFile().toPath());
        } catch (IOException e) {
            log.warn("Could not determine file type.");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(documentDTO.getResource());
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable long id) {
        log.info("Request for deleting med_card file with id  {}", id);
        documentService.deleteMedCardFile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
