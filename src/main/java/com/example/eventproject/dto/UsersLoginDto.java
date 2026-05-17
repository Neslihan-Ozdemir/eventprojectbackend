package com.example.eventproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersLoginDto {

    @NotNull
    @Email
    @NotEmpty
    @Size(min = 5, max= 100)
    String email;

    @NotNull
    @NotEmpty
    @Size(min=6 , max=20)
    String password;

}
