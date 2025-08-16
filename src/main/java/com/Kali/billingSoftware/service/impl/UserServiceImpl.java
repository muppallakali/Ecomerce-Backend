package com.Kali.billingSoftware.service.impl;

import com.Kali.billingSoftware.entity.UserEntity;
import com.Kali.billingSoftware.io.UserRequest;
import com.Kali.billingSoftware.io.UserResponse;
import com.Kali.billingSoftware.repository.UserRepository;
import com.Kali.billingSoftware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
 private UserRepository userRepository;
    @Autowired
 private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity newUser=convertToEntity(request);
        UserEntity savedUser = userRepository.save(newUser);
        return convertToResponse(savedUser);
    }

    private UserResponse convertToResponse(UserEntity newUser) {
        return UserResponse.builder()
                .userId(newUser.getUserId())
                .role(newUser.getRole())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }

    private UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole().toUpperCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    @Override
    public String getUserRole(String email) {
        UserEntity existingUser= userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("No User with this EmailId: "+email));
                return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {
        return userRepository.findAll().stream()
                .map(user->convertToResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
        UserEntity existingUser=userRepository.findByUserId(id).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found")
        );
        userRepository.delete(existingUser);
    }
}
