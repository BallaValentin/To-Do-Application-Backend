package edu.bbte.idde.bvim2209.spring.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "^(user|admin)$", message = "Invalid role pattern")
    String role;
}
