package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.verify;

public class IndexControllerTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private IndexController indexController;
    @Mock
    private Pageable pageable;

    @Before
    public void setUp() {
        indexController = new IndexController();
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void setRepository(){
        indexController.setRepository(postRepository, categoryRepository);
        Assert.assertEquals(this.postRepository,postRepository);
        Assert.assertEquals(this.categoryRepository,categoryRepository);
    }

    @Test
    public void login() {
        Assert.assertEquals("person/login", indexController.login());
    }

    @Test
    public void index() {
        Assert.assertEquals("index", indexController.Index(pageable).getViewName());
        verify(categoryRepository).findAll();
        verify(postRepository).findAll(pageable);
    }

}
