package com.personal.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {
    @NotBlank
    private final String password;

    @NotBlank
    private final String name;

    public User(@JsonProperty("password") String password, @JsonProperty("name") String name) {
        this.password = password;
        this.name = name;
    }
}
