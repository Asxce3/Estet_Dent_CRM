package org.example.test_orm.repository.directories;

import org.example.test_orm.entity.Category;
import org.example.test_orm.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryRepo extends JpaRepository<Directory, Long> {

    List<Directory> findByCategory(Category category);

}
