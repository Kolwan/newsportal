/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.newsportalspringboot.repositories;

import com.student.newsportalspringboot.entities.Post;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    Page<Post> findAllByCategory(String category, Pageable pageable);
    Set<Post> findAllByCategory(String category);    
    long countByCategory(String category);

}
