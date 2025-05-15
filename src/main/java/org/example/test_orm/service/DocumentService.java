package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.DTO.DocumentDTO;
import org.example.test_orm.entity.Document;
import org.example.test_orm.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    @Value("${DIRECTORY_TO_UPLOAD_FILES}")
    private final String path;

    private final DocumentRepository documentRepository;


    public List<Document> createFiles(List<MultipartFile> files) {
        List<Document> documents = new ArrayList<>();
        for (MultipartFile requestFile : files) {
            String fileName = requestFile.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            String fileExtension = fileName.substring(index);
            String uuidName = UUID.randomUUID() + fileExtension;

            log.info("Try to save the file with name {}", fileName);
            try(FileOutputStream fos = new FileOutputStream(path + uuidName)) {
                fos.write(requestFile.getBytes());
                log.info("File {} has been written successfully!", fileName);
                documents.add(createDocument(fileName, uuidName));
                log.info("Document has been created successfully!");
            }   catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return documents;
    }

    private Document createDocument(String fileName, String uuidName) {
        Document document = new Document();
        document.setFileName(fileName);
        document.setPath(path + uuidName);
        return document;
    }
    public DocumentDTO getMedCardFile(long id) {
        try {
            log.info("Search document in DB by id for get{}", id);
            Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documents not found!"));
            log.info("Find document success!");
            log.info("Try find resource in system");
            Resource resource = new UrlResource("file:" + document.getPath());
            if(!resource.exists()) {
                throw new RuntimeException("File not found!");
            }
            log.info("Find Resource success!");
            return new DocumentDTO(document.getFileName(), resource);
        }   catch (MalformedURLException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteMedCardFile(long id) {
        log.info("Search document in DB by id for delete{}", id);
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documents not found!"));
        File file = new File(document.getPath());
        if(file.delete()){
            documentRepository.deleteById(id);
            log.info("File delete successful!");
        };
    }

}
