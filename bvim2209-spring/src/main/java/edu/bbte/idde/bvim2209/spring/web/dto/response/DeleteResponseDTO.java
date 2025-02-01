package edu.bbte.idde.bvim2209.spring.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteResponseDTO {
    private Integer deletedRows;
}
