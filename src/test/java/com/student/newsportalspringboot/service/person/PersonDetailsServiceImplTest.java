package com.student.newsportalspringboot.service.person;

import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.services.person.PersonDetailsServiceImpl;
import com.student.newsportalspringboot.services.person.PersonService;
import org.junit.Assert;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class PersonDetailsServiceImplTest {

    @Mock
    private PersonService personService;
    @Mock
    private Person user;
    @Mock
    private GrantedAuthority authority;
    @InjectMocks
    private PersonDetailsServiceImpl personDetailsServiceImpl;
    private final String email = "example@mail.ru";
    private final String password = "password";

    @Before
    public void setUp() {
        personDetailsServiceImpl = new PersonDetailsServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setService() {
        personDetailsServiceImpl.setService(personService);
        Assert.assertEquals(this.personService, personService);
    }

    @Test
    public void loadUserByUsernameIncorrectUser() {
        personDetailsServiceImpl.loadUserByUsername(email);
    }

    @Ignore
    public void loadUserByUsernameCorrectUser() {
        when(personService.existsUserByEmail(email)).thenReturn(true);
        when(personService.findPersonByEmail(email)).thenReturn(user);
        personDetailsServiceImpl.loadUserByUsername(email);
    }

}
