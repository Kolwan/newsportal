package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.entities.ProfilePerson;
import com.student.newsportalspringboot.services.person.PersonService;
import com.student.newsportalspringboot.validator.PersonValidator;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {

    @Value("${default.user.name}")
    private String name;

    private PersonService personService;
    @Autowired
    PersonValidator personValidator;

    @Autowired
    public void setService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("login")
    public String login() {
        return "person/login";
    }

    @GetMapping("user")
    public ModelAndView getProfileUser(Person person, Principal principal) {
        ModelAndView mav = new ModelAndView("person/profilePerson");
        String username = principal.getName();
        if (!username.equals(name)) {
            mav.addObject("profilePerson", personService.findPersonByEmail(username));
            mav.addObject("person", personService.findPersonByEmail(username));
        }
        return mav;
    }

    @RequestMapping("user/{id}")
    public ModelAndView getUser(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("person/profile");
        mav.addObject("person", personService.getPersonById(id));
        return mav;
    }

    @PostMapping("user")
    public ModelAndView saveProfileUser(@Valid ProfilePerson profilePerson, BindingResult bindingResult, Person person, Principal principal) {
        ModelAndView mav = getProfileUser(person, principal);
        if (bindingResult.hasErrors()) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.setViewName("person/profilePerson");
            return mav;
        }

        String username = principal.getName();
        person = personService.findPersonByEmail(username);
        person.setName(profilePerson.getName());
        person.setSurname(profilePerson.getSurname());
        person.setUrlImage(profilePerson.getUrlImage());
        person.setSignature(profilePerson.getSignature());
        personService.saveProfile(person);
        mav.setViewName("redirect:user");
        return mav;
    }

}
