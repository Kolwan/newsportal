package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Admin;
import com.student.newsportalspringboot.entities.Category;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.entities.Post;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import com.student.newsportalspringboot.services.person.PersonService;
import com.student.newsportalspringboot.services.post.PageWrapper;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView showPersons(Person person) {
        ModelAndView mav = new ModelAndView("admin/persons");
        mav.addObject("admin", new Admin());
        mav.addObject("person", personService.listAllPerson());
        return mav;
    }

    @RequestMapping("admin/category")
    public ModelAndView getCategory(Category category) {
        ModelAndView mav = new ModelAndView("admin/listCategory");
        mav.addObject("category", new Category());
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @PostMapping("admin/person")
    public String saveAdmin(@Valid Admin admin, Model model) {
        if (admin.getEmail().equals(name)) {
            return "redirect:/admin/person";
        }
        personService.saveAdmin(admin);
        return "redirect:/admin/person";
    }

    @RequestMapping("admin/person/settings/{id}/delete")
    public String deleteAdmin(@PathVariable Integer id, Person person, Model model) {
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }

    @RequestMapping("admin/person/settings/{id}/blocked")
    public String blockedPerson(@PathVariable Integer id, Person person, Model model) {
        person = personService.getPersonById(id);
        personService.blockPerson(person);
        return "redirect:/admin/person";
    }

    @RequestMapping("admin/person/settings/{id}/unblocked")
    public String unblockedPerson(@PathVariable Integer id, Person person, Model model) {
        person = personService.getPersonById(id);
        personService.unblockPerson(person);
        return "redirect:/admin/person";
    }

    @PostMapping("admin/category")
    public String SaveCategory(Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @RequestMapping("admin/category/{id}/delete")
    public String deleteCategory(@PathVariable Integer id, Category category, Model model) {
        categoryRepository.delete(id);
        return "redirect:/admin/category";
    }

    @GetMapping("admin/post/list/all")
    public ModelAndView getAllPost(Pageable pageable) {
        ModelAndView mav = new ModelAndView("admin/listPost");
        Page<Post> postPage = postRepository.findAll(pageable);
        PageWrapper<Post> page = new PageWrapper<>(postPage, "admin/post/list/all");
        mav.addObject("post", page.getContent());
        mav.addObject("page", page);
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }

    @GetMapping("admin/post/list/{category}")
    public ModelAndView getPostByCategory(@PathVariable String category, @PageableDefault(sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView("admin/listPost");
        Page<Post> postPage = postRepository.findAllByCategory(category, pageable);
        PageWrapper<Post> page = new PageWrapper<>(postPage, "/admin/post/list/" + category);
        mav.addObject("post", page.getContent());
        mav.addObject("page", page);
        mav.addObject("category", categoryRepository.findAll());
        return mav;
    }
}
