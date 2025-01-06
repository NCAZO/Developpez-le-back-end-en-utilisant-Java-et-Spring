package com.openclassrooms.chatop.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class RegisterRequestDto {

    @NotBlank(message = "L'email est obligatoire !")
    private String email;

    @NotBlank(message = "Le nom est obligatoire !")
    private String name;

    @NotBlank(message = "Le mot de passe est obligatoire !")
    private String password;

    private Date created_at;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    ////////// GETTER SETTER


}