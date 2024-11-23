package qwerdsa53.restfultasklist.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import qwerdsa53.restfultasklist.dto.UserRegistrationDto;
import qwerdsa53.restfultasklist.entity.User;
import qwerdsa53.restfultasklist.repo.UserRepo;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    public void registerUser(UserRegistrationDto userDto) {
        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole("USER");
        user.setEnabled(true);
        userRepo.save(user);
    }
}