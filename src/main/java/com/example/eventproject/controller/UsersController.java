package com.example.eventproject.controller;



import com.example.eventproject.dto.UsersLoginDto;
import com.example.eventproject.dto.UsersRegisterDto;
import com.example.eventproject.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody UsersRegisterDto usersRegisterDto) {
        return usersService.register(usersRegisterDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UsersLoginDto usersLoginDto) {
        return usersService.login(usersLoginDto);
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        return usersService.logout();
    }
}
