package edu.bbte.idde.bvim2209.spring.web.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String fullname;
    private String jwtToken;
}
