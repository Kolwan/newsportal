package com.student.newsportalspringboot.services.person;

import com.student.newsportalspringboot.entities.Admin;
import com.student.newsportalspringboot.entities.Person;

public interface PersonService {

    Iterable<Person> listAllPerson();

    Person getPersonById(Integer id);

    Person saveAdmin(Admin admin);

    void deletePerson(Integer id);

    Person findPersonByEmail(String email);

    Person blockPerson(Person person);

    Person unblockPerson(Person person);

}
