package com.student.newsportalspringboot.repositories;

import com.student.newsportalspringboot.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByEmail(String email);
    Set<Person> findAllByRolePerson(String role);

}
