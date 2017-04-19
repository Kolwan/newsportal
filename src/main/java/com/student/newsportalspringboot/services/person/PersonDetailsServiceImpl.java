/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.newsportalspringboot.services.person;

import com.student.newsportalspringboot.entities.Person;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author student
 */
@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    private PersonService personService;

    @Autowired
    public void setService(PersonService personService) {

        this.personService = personService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = personService.findPersonByEmail(email);

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRolePerson());
        UserDetails userDetails = (UserDetails) new User(user.getEmail(),
                user.getPassword(),true, true, true,user.isNonLocked(), Arrays.asList(authority));
        return userDetails;
    }

}
