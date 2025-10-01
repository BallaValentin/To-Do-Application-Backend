package edu.bbte.idde.bvim2209.spring.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginReqDTO {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private Boolean rememberMe;
}
