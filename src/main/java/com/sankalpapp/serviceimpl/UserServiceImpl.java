package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Role;
import com.sankalpapp.entity.User;
import com.sankalpapp.repository.PaymentRepository;
import com.sankalpapp.repository.RoleRepository;
import com.sankalpapp.repository.UserRepository;
import com.sankalpapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (userRepository.existsByMobile(user.getMobile())) {
            throw new RuntimeException("Mobile already exists.");
        }

        // Fetch Role from DB
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        // Encrypt Password

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = getUserById(id);

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());
        existingUser.setActive(user.getActive());

        // Fetch Role from DB
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingUser.setRole(role);

        // Update password only if provided
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }
}