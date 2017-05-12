package com.student.newsportalspringboot.services.person;

import com.student.newsportalspringboot.entities.Admin;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private PersonRepository personRepository;

    private final String roleAdmin = "ROLE_ADMIN";

    @Autowired
    public void set(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }

    @Override
    public Iterable<Person> listAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Integer id) {
        return personRepository.findOne(id);
    }

    @Override
    public Person saveAdmin(Admin admin) {
        Person person = new Person();
        person.setName(admin.getName());
        person.setSurname(admin.getSurname());
        person.setEmail(admin.getEmail());
        person.setPassword(passwordEncoder.encode(admin.getPassword()));
        person.setNonLocked(true);
        person.setRolePerson(roleAdmin);
        return personRepository.save(person);

    }

    @Override
    public Person unblockPerson(Person person) {
        person.setNonLocked(true);
        return personRepository.save(person);
    }

    @Override
    public Person blockPerson(Person person) {
        person.setNonLocked(false);
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Integer id) {
        personRepository.delete(id);

    }

    @Override
    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

}
