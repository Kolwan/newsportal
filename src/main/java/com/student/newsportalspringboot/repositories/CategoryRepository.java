package com.student.newsportalspringboot.repositories;

import com.student.newsportalspringboot.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    boolean existsByUrl(String url);

    Category findByUrl(String utl);

}
