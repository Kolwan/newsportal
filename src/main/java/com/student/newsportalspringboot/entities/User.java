/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.newsportalspringboot.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author student
 */
public class User implements Serializable {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 15, message = "Некорректное имя")
    private String name;
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 20, message = "Некорректная фамилия")
    private String surname;
    @Size(min = 6, message = "Пароль должен быть более 6 символов")
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    

}
