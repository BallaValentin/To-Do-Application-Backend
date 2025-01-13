package edu.bbte.idde.bvim2209.spring.web.dto.response;

import lombok.Data;

@Data
public class AdminUserRespDTO {
    Long id;
    String fullname;
    String username;
    String email;
}
