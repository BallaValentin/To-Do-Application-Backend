package edu.bbte.idde.bvim2209.spring.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterReqDTO {
    @NotBlank(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{3,}$", message = "Invalid username pattern")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$",
        message = "Invalid password pattern")
    private String password;

    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be blank")
    @Pattern(regexp = "^[A-Z][a-z]+ [A-Z][a-z]+$", message = "Invalid full name pattern")
    private String fullname;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email pattern")
    private String email;
}
