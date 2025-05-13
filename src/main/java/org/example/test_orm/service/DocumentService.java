package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.entity.Document;
import org.example.test_orm.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private DocumentRepository documentRepository;
    @Value("${DIRECTORY_TO_UPLOAD_FILES}")
    private final String path;


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

}
