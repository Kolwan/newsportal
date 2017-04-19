package com.student.newsportalspringboot.validator;

import com.student.newsportalspringboot.entities.User;
import com.student.newsportalspringboot.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonService personService;

    @Autowired
    public void setService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword", "Пароли не совпадают");

        }

        if (personService.findPersonByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "email", "Данный email уже зарегистрирован");
        }
    }

}
