package com.example.galatasaraystore.service;

import com.example.galatasaraystore.model.dto.UserRequestDto;
import com.example.galatasaraystore.model.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long id);
}


