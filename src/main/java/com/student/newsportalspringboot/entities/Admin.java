package com.student.newsportalspringboot.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class Admin implements Serializable {

    @Email
    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;
    @Size(min = 6, message = "Пароль должен быть более 6 символов")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
