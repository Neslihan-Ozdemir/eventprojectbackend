package com.example.eventproject.service;

import com.example.eventproject.dto.UsersLoginDto;
import com.example.eventproject.dto.UsersRegisterDto;
import com.example.eventproject.dto.UsersResponseDto;
import com.example.eventproject.entity.Users;
import com.example.eventproject.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    final UsersRepository usersRepository;
    final ModelMapper modelMapper;
    final HttpServletRequest request;

    public ResponseEntity<?> register(UsersRegisterDto usersRegisterDto) {
        Optional<Users> usersOptional = usersRepository.findByEmailEqualsIgnoreCase(usersRegisterDto.getEmail());
        if (usersOptional.isPresent()) {
            Map<String, Object> hm = Map.of("success", false, "message", "This email is already in use.");
            return ResponseEntity.badRequest().body(hm);
        }

        Users user = modelMapper.map(usersRegisterDto, Users.class);
        String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashPassword);
        user = usersRepository.save(user);
        UsersResponseDto usersResponseDto = modelMapper.map(user, UsersResponseDto.class);
        return ResponseEntity.ok().body(usersResponseDto);
    }

    // login
    public ResponseEntity<?> login(UsersLoginDto usersLoginDto) {
        Optional<Users> usersOptional = usersRepository.findByEmailEqualsIgnoreCase(usersLoginDto.getEmail());
        if (usersOptional.isPresent()) {
            Users user = usersOptional.get();
            boolean isMatch = BCrypt.checkpw(usersLoginDto.getPassword(), user.getPassword());
            if (isMatch) {
                UsersResponseDto usersResponseDto = modelMapper.map(user, UsersResponseDto.class);
                request.getSession().setAttribute("user", usersResponseDto);
                return ResponseEntity.ok().body(usersResponseDto);
            }
        }
        Map<String, Object> hm = Map.of("success", false, "message", "Username or password is incorrect.");
        return ResponseEntity.badRequest().body(hm);
    }

    public ResponseEntity<?> logout() {
        request.getSession().invalidate();
        return ResponseEntity.ok().body("Logout successfully.");
    }
}


