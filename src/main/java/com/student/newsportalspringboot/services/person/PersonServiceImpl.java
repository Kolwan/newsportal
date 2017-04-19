package com.student.newsportalspringboot.services.person;

import com.student.newsportalspringboot.entities.User;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.repositories.PersonRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private PersonRepository personRepository;

    private final String roleUser = "ROLE_USER";
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
    public Person savePerson(User user) {
        Person person = new Person();
        person.setEmail(user.getEmail());
        person.setName(user.getName());
        person.setSurname(user.getSurname());
        person.setRolePerson(roleUser);
        person.setNonLocked(true);
        person.setPassword(passwordEncoder.encode(user.getPassword()));
        return personRepository.save(person);

    }

    @Override
    public Person saveProfile(Person person) {

        return personRepository.save(person);
    }

    @Override
    public Person saveAdmin(Person person) {
        person.setRolePerson(roleAdmin);
        return personRepository.save(person);

    }

    @Override
    public Person removeAdmin(Person person) {
        person.setRolePerson(roleUser);
        return personRepository.save(person);
    }

    @Override
    public Person unblockPerson(Person person) {
        if (person.getRolePerson().equals(roleUser)) {
            person.setNonLocked(true);
        }
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

    @Override
    public Set<Person> findAllByRole(String role) {
        return personRepository.findAllByRolePerson(role);
    }
}
