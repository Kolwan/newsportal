package com.student.newsportalspringboot.entities;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.*;

public class ProfilePerson implements Serializable {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 15, message = "Некорректное имя")
    private String name;
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 20, message = "Некорректная фамилия")
    private String surname;
    @URL(message = "Не является Url")
    private String urlImage;
    @Size(max = 50, message = "Подпись больше 50 знаков")
    private String signature;

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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
