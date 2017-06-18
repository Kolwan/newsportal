package com.student.newsportalspringboot;

import com.student.newsportalspringboot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controller1 {

    private PersonRepository personRepository;

    @Autowired
    public void setRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("admin/person/deleteAll")
    public String deleteAll() {
        personRepository.deleteAll();
        return "redirect:/admin"
    }
}
