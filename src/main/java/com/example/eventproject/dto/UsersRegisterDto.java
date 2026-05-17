package com.example.eventproject.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsersRegisterDto {

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    @NotBlank
    String username;

    @NotNull
    @Size(min = 5, max = 100)
    @Email
    @NotEmpty
    @NotBlank
    String email;

    @NotNull
    @Size(min = 6, max = 20)
    @NotEmpty
    @NotBlank
    String password;
}