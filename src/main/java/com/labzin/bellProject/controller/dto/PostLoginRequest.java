package com.labzin.bellProject.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginRequest {

    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 20)
    private String login;

    @NotBlank(message = "Password is required")
    @Size(min = 3, max = 20)
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
}
