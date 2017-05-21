package com.student.newsportalspringboot.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "persons")
public class Person implements Serializable {

    @Id
    @Column(name = "person_id")
    private int id;

    private String name;

    private String surname;

    private String password;

    private String email;

    private String rolePerson;

    private boolean nonLocked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolePerson() {
        return rolePerson;
    }

    public void setRolePerson(String rolePerson) {
        this.rolePerson = rolePerson;
    }

    public boolean isNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", password=" + password + ", email=" + email + ", rolePerson=" + rolePerson + ", nonLocked=" + nonLocked + '}';
    }

}
