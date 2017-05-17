package com.student.newsportalspringboot.service.person;

import com.student.newsportalspringboot.entities.Admin;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.repositories.PersonRepository;
import com.student.newsportalspringboot.services.person.PersonServiceImpl;
import org.junit.Assert;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private Person person;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Admin admin;

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    private final int id = 1;
    private final String email = "example@mail.ru";

    @Before
    public void setUp() {
        personServiceImpl = new PersonServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setRepository() {
        personServiceImpl.setRepository(personRepository);
        Assert.assertEquals(this.personRepository, personRepository);

    }

    @Test
    public void listAllPerson() {
        personServiceImpl.listAllPerson();
        verify(personRepository).findAll();
    }

    @Test
    public void getPersonById() {
        personServiceImpl.getPersonById(id);
        verify(personRepository).findOne(id);
    }

    @Test
    public void saveAdmin() {
        personServiceImpl.saveAdmin(admin);
        verify(passwordEncoder).encode(admin.getPassword());

    }

    @Test
    public void unblockPerson() {
        personServiceImpl.unblockPerson(person);
        verify(person).setNonLocked(true);
        verify(personRepository).save(person);
    }

    @Test
    public void blockPerson() {
        personServiceImpl.blockPerson(person);
        verify(person).setNonLocked(false);
        verify(personRepository).save(person);
    }

    @Test
    public void deletePerson() {
        personServiceImpl.deletePerson(id);
        verify(personRepository).delete(id);
    }

    @Test
    public void findPersonByEmail() {
        personServiceImpl.findPersonByEmail(email);
    }
    
    @Test
    public void existsUserByEmail(){
        personServiceImpl.existsUserByEmail(email);
    }

}
