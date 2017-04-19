package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.User;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @GetMapping("/registration")
    public ModelAndView Registration() {
        ModelAndView mav = new ModelAndView("person/newUser");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/registration")
    public ModelAndView savePerson(@Valid User user, BindingResult bindingResult) {
        ModelAndView mav = Registration();
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }
        personService.savePerson(user);
        mav.setViewName("redirect:/login");
        return mav;
    }

    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable Integer id) {
        personService.deletePerson(id);
        return "";
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

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Integer id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));
        return "person/profile";
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
