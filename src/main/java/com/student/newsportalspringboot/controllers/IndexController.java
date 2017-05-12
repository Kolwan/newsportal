package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelAndView mav;
    private final int MAX_SIZE_PAGES = 5;

    @Autowired
    public void setRepository(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @RequestMapping("login")
    public String login() {
        return "person/login";
    }

    @GetMapping("/")
    public ModelAndView Index(@PageableDefault(page = 0, size = MAX_SIZE_PAGES, sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        mav = new ModelAndView("index");
        mav.addObject("post", postRepository.findAll(pageable));
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

}
