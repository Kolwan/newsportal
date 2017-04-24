package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Post;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setRepository(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @RequestMapping("/post/{category}/{id}")
    public ModelAndView showPost(@PathVariable String id, @PathVariable String category) {
        ModelAndView mav = new ModelAndView("news/post");
        try {
            if (postRepository.exists(Integer.parseInt(id))) {
                mav.addObject("post",postRepository.findOne(Integer.parseInt(id)) );
                mav.addObject("category", categoryRepository.findAll());
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

    @RequestMapping("admin/post/{category}/{id}/alter")
    public ModelAndView editPost(@PathVariable Integer id, @PathVariable String category) {
        ModelAndView mav = new ModelAndView("news/addPost");
        mav.addObject("post", postRepository.findOne(id));
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @RequestMapping("admin/post/new")
    public ModelAndView newPost() {
        ModelAndView mav = new ModelAndView("news/addPost");
        mav.addObject("post", new Post());
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @PostMapping("post/save")
    public String savePost(Post post) {
        if (postRepository.findOne(post.getId()) != null) {
            Post news = postRepository.findOne(post.getId());
            post.setDatePublication(news.getDatePublication());
        }
        if (post.getDatePublication() == null) {
            post.setDatePublication(new Date());
        }
        postRepository.save(post);
        return "redirect:/post/" + post.getCategory() + "/" + post.getId();
    }

    @RequestMapping("admin/post/{category}/{id}/delete")
    public String delete(@PathVariable Integer id) {
        postRepository.delete(id);
        return "redirect:/admin/post/list/all";
    }
}
