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

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    private PersonService personService;
    private Person user;
    private GrantedAuthority authority;
    private UserDetails userDetails;

    @Autowired
    public void setService(PersonService personService) {

        this.personService = personService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (personService.existsUserByEmail(email)) {
            user = personService.findPersonByEmail(email);
            authority = new SimpleGrantedAuthority(user.getRolePerson());
            userDetails = new User(user.getEmail(), user.getPassword(), true, true, true, user.isNonLocked(), Arrays.asList(authority));
            return userDetails;
        }
        userDetails = new User("user", "123", false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("user")));
        return userDetails;
    }

}
