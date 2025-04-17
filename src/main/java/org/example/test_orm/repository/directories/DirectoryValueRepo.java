package org.example.test_orm.repository.directories;

import org.example.test_orm.entity.Directory;
import org.example.test_orm.entity.DirectoryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryValueRepo extends JpaRepository<DirectoryValue, Long> {
    List<DirectoryValue> findByDirectory(Directory directory);
}
