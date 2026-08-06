package com.sankalpapp.service;

import com.sankalpapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);
}