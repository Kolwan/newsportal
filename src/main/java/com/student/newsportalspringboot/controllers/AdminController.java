package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Admin;
import com.student.newsportalspringboot.entities.Category;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.entities.Post;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import com.student.newsportalspringboot.services.person.PersonService;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    private PersonService personService;
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelAndView mav;
    private Person person;
    private Post news;
    private Page<Post> page;
    private final Sort sort = new Sort(Sort.Direction.DESC, "datePublication");
    private final int MAX_SIZE_PAGES = 100;

    @Value("${default.user.name}")
    private String name;

    @Autowired
    public void setPostService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setRepository(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @RequestMapping("admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("admin/person")
    public ModelAndView showAdmins() {
        mav = new ModelAndView("admin/listPerson");
        mav.addObject("admin", new Admin());
        mav.addObject("category", categoryRepository.findAll());
        mav.addObject("person", personService.listAllPerson());
        return mav;
    }

    @PostMapping("admin/person")
    public String saveAdmin(@Valid Admin admin) {
        if (!admin.getEmail().equals(name)) {
            personService.saveAdmin(admin);
        }
        return "redirect:/admin/person";
    }

    @RequestMapping("admin/person/settings/{id}/delete")
    public String deleteAdmin(@PathVariable Integer id) {
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }

    @RequestMapping("admin/person/settings/{id}/blocked")
    public String blockedPerson(@PathVariable Integer id) {
        person = personService.getPersonById(id);
        personService.blockPerson(person);
        return "redirect:/admin/person";
    }

    @RequestMapping("admin/person/settings/{id}/unblocked")
    public String unblockedPerson(@PathVariable Integer id) {
        person = personService.getPersonById(id);
        personService.unblockPerson(person);
        return "redirect:/admin/person";
    }

    @GetMapping("admin/post/all")
    public ModelAndView getAllPost() {
        mav = new ModelAndView("admin/listPost");
        page = postRepository.findAll(new PageRequest(0, MAX_SIZE_PAGES, sort));
        mav.addObject("post", page.getContent());
        mav.addObject("page", page);
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @GetMapping("admin/post/{category}")
    public ModelAndView getAllPostByCategory(@PathVariable String category) {
        mav = new ModelAndView("admin/listPost");
        page = postRepository.findAllByCategory(category, new PageRequest(0, MAX_SIZE_PAGES, sort));
        mav.addObject("post", page.getContent());
        mav.addObject("page", page);
        mav.addObject("categoryPost", categoryRepository.findByUrl(category));
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @RequestMapping("admin/post/new")
    public ModelAndView newPost() {
        mav = new ModelAndView("news/addPost");
        mav.addObject("post", new Post());
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @RequestMapping("admin/post/{category}/{id}/alter")
    public ModelAndView editPost(@PathVariable Long id, @PathVariable String category) {
        mav = new ModelAndView("news/addPost");
        mav.addObject("post", postRepository.findOne(id));
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @PostMapping("admin/post/save")
    public String savePost(Post post) {
        if (post.getDatePublication() == null) {
            post.setDatePublication(new Date());
        }
        if (postRepository.exists(post.getId())) {
            news = postRepository.findOne(post.getId());
            post.setDatePublication(news.getDatePublication());
        }
        postRepository.save(post);
        return "redirect:/post/" + post.getCategory() + "/" + post.getId();
    }

    @RequestMapping("admin/post/{category}/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postRepository.delete(id);
        return "redirect:/admin/post/all";
    }

    @RequestMapping("admin/category")
    public ModelAndView getCategory() {
        mav = new ModelAndView("admin/listCategory");
        mav.addObject("category", new Category());
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @PostMapping("admin/category")
    public String SaveCategory(Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @RequestMapping("admin/category/{id}/delete")
    public String deleteCategory(@PathVariable Integer id) {
        categoryRepository.delete(id);
        return "redirect:/admin/category";
    }
}
