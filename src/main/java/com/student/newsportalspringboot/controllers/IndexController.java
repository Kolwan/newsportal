package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Post;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import com.student.newsportalspringboot.services.post.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setRepository(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("post/all")
    public ModelAndView list(@PageableDefault(page = 0, size = 5, sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView("news/listPost");
        Page<Post> postPage = postRepository.findAll(pageable);
        PageWrapper<Post> page = new PageWrapper<>(postPage, "/post/all");
        mav.addObject("post", page.getContent());
        mav.addObject("page", page);
        mav.addObject("category", categoryRepository.findAll());

        return mav;
    }

    @RequestMapping("/")
    public ModelAndView Index(@PageableDefault(page = 0, size = 5, sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("post", postRepository.findAll(pageable));
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @RequestMapping("post/{category}")
    public ModelAndView ListPost(@PathVariable String category, @PageableDefault(sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        if (categoryRepository.existsByUrl(category)) {
            mav.setViewName("news/listPost");
            Page<Post> postPage = postRepository.findAllByCategory(category, pageable);
            PageWrapper<Post> page = new PageWrapper<>(postPage, "/post/" + category);
            mav.addObject("post", page.getContent());
            mav.addObject("page", page);
            mav.addObject("category", categoryRepository.findAll());
        } else {
            mav.setViewName("redirect:/post/all?page=0&size=5");
        }
        return mav;
    }
}
