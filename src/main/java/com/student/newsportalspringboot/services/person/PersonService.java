package com.student.newsportalspringboot.services.person;

import com.student.newsportalspringboot.entities.User;
import com.student.newsportalspringboot.entities.Person;
import java.util.Set;

public interface PersonService {

    Iterable<Person> listAllPerson();

    Person getPersonById(Integer id);

    Person savePerson(User user);
    
    Person saveAdmin(Person person);

    void deletePerson(Integer id);

    Person findPersonByEmail(String email);
    
    Set<Person> findAllByRole(String role);
    
    Person blockPerson(Person person);
    
    Person unblockPerson(Person person);
    
    Person removeAdmin(Person person);
    
    Person saveProfile(Person person);

}
