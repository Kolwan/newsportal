package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public class IndexControllerTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private IndexController ic;
    
    

    @Before
    public void setUp() {
        ic = new IndexController();
    }

    @Test
    public void TestIndex() {

    }

}
