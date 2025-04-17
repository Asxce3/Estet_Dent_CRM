package org.example.test_orm.service.test;

import lombok.RequiredArgsConstructor;

import org.example.test_orm.entity.Directory;
import org.example.test_orm.entity.DirectoryValue;
import org.example.test_orm.repository.directories.DirectoryRepo;
import org.example.test_orm.repository.directories.DirectoryValueRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryRepo directoryRepo;
    private final DirectoryValueRepo directoryValueRepo;


    public List<Directory> getThree() {
        List<Directory> three = new ArrayList<>();
        List<Directory> generalDirectories = directoryRepo.findByParentIsNull();
        generalDirectories.forEach(v-> System.out.println(v.getName()));
        for(Directory directory : generalDirectories) {
            long threeId = directory.getThreeId();
            List<Directory> children = directoryRepo.findByThreeId(threeId).stream().map(this::convert).toList();
            three.addAll(children);
        }
        return three;

    }
//
//    public void run() {
//        qwe();
//    }

    private Directory convert(Directory directory) {
        List<DirectoryValue> values = directoryValueRepo.findByDirectory(directory);
        if(!values.isEmpty()) {
            directory.setValues(values);
        }
        return directory;
    }




}
