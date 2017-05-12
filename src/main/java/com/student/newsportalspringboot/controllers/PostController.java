package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Category;
import com.student.newsportalspringboot.entities.Post;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelAndView mav;
    private Category c;
    private PageRequest pageRequest;
    private Page<Post> page;
    private final int MAX_SIZE_PAGES = 5;
    private final Sort sort = new Sort(Sort.Direction.DESC, "datePublication");

    @Autowired
    public void setRepository(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("post")
    public String post() {
        return "redirect:/post/page/1";
    }

    @RequestMapping("post/{category}")
    public String postByCategory(@PathVariable String category) {
        return "redirect:/post/{category}/page/1";
    }

    @GetMapping("/post/{category}/{id}")
    public ModelAndView showPost(@PathVariable String id, @PathVariable String category) {
        mav = new ModelAndView("news/post");
        try {
            if (postRepository.exists(Long.parseLong(id))) {
                c = categoryRepository.findByUrl(category);
                mav.addObject("post", postRepository.findOne(Long.parseLong(id)));
                mav.addObject("category", categoryRepository.findAll());
                mav.addObject("categoryName", c.getName());
            } else {
                mav.setStatus(HttpStatus.NOT_FOUND);
                mav.setViewName("error/404");
            }
        } catch (NumberFormatException e) {
            mav.setStatus(HttpStatus.NOT_FOUND);
            mav.setViewName("error/404");
        }
        return mav;
    }

    @GetMapping("post/page/{numberPage}")
    public ModelAndView showAllPost(@PathVariable String numberPage) {
        mav = new ModelAndView("news/listPost");
        try {
            pageRequest = new PageRequest(Integer.parseInt(numberPage) - 1, MAX_SIZE_PAGES, sort);
            page = postRepository.findAll(pageRequest);
            if (page.getTotalPages() < Integer.parseInt(numberPage)) {
                mav.setViewName("redirect:/");
                return mav;
            }
            mav.addObject("post", page.getContent());
            mav.addObject("page", page);
            mav.addObject("category", categoryRepository.findAll());
        } catch (Exception e) {
            mav.setViewName("redirect:/post/page/1");
            return mav;
        }
        return mav;
    }

    @GetMapping("post/{category}/page/{numberPage}")
    public ModelAndView showPostByCategory(@PathVariable String category, @PathVariable String numberPage) {
        mav = new ModelAndView("news/listPost");
        if (categoryRepository.existsByUrl(category)) {
            Category c = categoryRepository.findByUrl(category);
            try {
                pageRequest = new PageRequest(Integer.parseInt(numberPage) - 1, MAX_SIZE_PAGES, sort);
                page = postRepository.findAllByCategory(c.getUrl(), pageRequest);
                if (page.getTotalPages() < Integer.parseInt(numberPage)) {
                    mav.setViewName("redirect:/post/page/1");
                    return mav;
                }
                mav.addObject("post", page.getContent());
                mav.addObject("page", page);
                mav.addObject("categoryName", c.getName());
                mav.addObject("category", categoryRepository.findAll());
            } catch (Exception e) {
                mav.setViewName("redirect:/post/" + c.getUrl() + "/page/1");
                return mav;
            }
        } else {
            mav.setViewName("redirect:/post/page/1");
        }
        return mav;
    }
}
