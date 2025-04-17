package org.example.test_orm.repository.directories;

import org.example.test_orm.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryRepo extends JpaRepository<Directory, Long> {

    List<Directory> findByThreeId(long id);
    List<Directory> findByParentIsNull();
    List<Directory> findByParent(Directory directory);
}
