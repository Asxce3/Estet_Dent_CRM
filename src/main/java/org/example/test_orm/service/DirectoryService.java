package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.DTO.DirectoryDTO;
import org.example.test_orm.DTO.DirectoryValueDTO;
import org.example.test_orm.entity.Category;
import org.example.test_orm.entity.Directory;
import org.example.test_orm.entity.DirectoryValue;
import org.example.test_orm.repository.directories.DirectoryRepo;
import org.example.test_orm.repository.directories.DirectoryValueRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class DirectoryService {
    private final DirectoryRepo directoryRepo;
    private final DirectoryValueRepo directoryValueRepo;


    public List<DirectoryDTO> getThree() {
        List<DirectoryDTO> three = new ArrayList<>();
        Category[] categories = Category.values();
        for(Category category: categories) {
            List<Directory> directories = directoryRepo.findByCategory(category);
            List<DirectoryDTO> directoriesDTO = directories.stream().map(this::directoryMappingDTO).toList();

            List<DirectoryDTO> nodes = new ArrayList<>();

            for(int j = 0; j < directories.size(); j++) {
                if(directories.get(j).getParent() == null ) {
                    nodes.add(directoriesDTO.get(j));
                    continue;
                }
                DirectoryDTO childDTO = directoriesDTO.get(j);

                int parentIndex = directories.indexOf(directories.get(j).getParent());

                DirectoryDTO parent = directoriesDTO.get(parentIndex);
                parent.getChildren().add(childDTO);
            }

            three.addAll(nodes);
        }

        return three;
    }


    private DirectoryDTO directoryMappingDTO(Directory directory) {
        DirectoryDTO directoryDTO = new DirectoryDTO();
        if(directory.getParent() != null) {
            directoryDTO.setParentId(directory.getParent().getId());
        }   else {
            directoryDTO.setParentId(null);
        }

        directoryDTO.setId(directory.getId());
        directoryDTO.setName(directory.getName());
        directoryDTO.setCategory(directory.getCategory());

        List<DirectoryValueDTO> values = directoryValueRepo.findByDirectory(directory)
                .stream().map(this::directoryValueMappingDTO).toList();
        if(!values.isEmpty()) {
            directoryDTO.setValues(values);
        }

        return directoryDTO;
    }

    private DirectoryValueDTO directoryValueMappingDTO(DirectoryValue directoryValue) {
        DirectoryValueDTO directoryValueDTO = new DirectoryValueDTO();
        directoryValueDTO.setId(directoryValue.getId());
        directoryValueDTO.setName(directoryValue.getName());
        directoryValueDTO.setDirectoryId(directoryValue.getDirectory().getId());
        return directoryValueDTO;
    }

    public DirectoryDTO createDirectory(DirectoryDTO directoryDTO) {
        try {
            Directory directory = DTOMappingDirectory(directoryDTO);
            Directory newDir = directoryRepo.save(directory);
            return directoryMappingDTO(newDir);

        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateDirectory(DirectoryDTO directoryDTO) {
        try {
            Directory directory = DTOMappingDirectory(directoryDTO);
            directory.setId(directoryDTO.getId());
            directoryRepo.save(directory);
        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private Directory DTOMappingDirectory(DirectoryDTO directoryDTO){
        Directory directory = new Directory();
        if(directoryDTO.getParentId() != null) {
            Optional<Directory> parent = directoryRepo.findById(directoryDTO.getParentId());
            parent.ifPresent(directory::setParent);
        }

        directory.setName(directoryDTO.getName());
        directory.setCategory(directoryDTO.getCategory());
        return directory;
    }

    public void deleteDirectory(long id) {
        try {
            directoryRepo.deleteById(id);
        }   catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    private DirectoryValue DTOMappingValue(DirectoryValueDTO valueDTO) {
        Optional<Directory> directory = directoryRepo.findById(valueDTO.getDirectoryId());
        DirectoryValue value = new DirectoryValue();

        directory.ifPresent(value::setDirectory);
        value.setName(valueDTO.getName());
        return value;
    }

    public DirectoryValueDTO createDirectoryValue(DirectoryValueDTO valueDTO) {
        try {
            DirectoryValue value = DTOMappingValue(valueDTO);
            DirectoryValue newValue = directoryValueRepo.save(value);
            return directoryValueMappingDTO(newValue);
        }   catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateDirectoryValue(DirectoryValueDTO valueDTO) {
        try {
            DirectoryValue value = DTOMappingValue(valueDTO);
            value.setId(valueDTO.getId());
            directoryValueRepo.save(value);
        }   catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteDirectoryValue(long id) {
        try {
            directoryValueRepo.deleteById(id);
        }   catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
        }
    }



}
