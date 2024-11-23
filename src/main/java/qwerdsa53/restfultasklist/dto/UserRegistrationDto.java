package qwerdsa53.restfultasklist.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
    private String confirmPassword;
    private String firstName;
    private String lastName;
}