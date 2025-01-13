package edu.bbte.idde.bvim2209.spring.web.mapper;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserLoginReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserRegisterReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.AdminUserRespDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.UserResponseDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User registerDTOToModel(UserRegisterReqDTO userRegisterReqDTO);

    public abstract User loginDTOToModel(UserLoginReqDTO userLoginReqDTO);

    public abstract UserResponseDTO userToResponseDTO(User user);

    @IterableMapping(elementTargetType = AdminUserRespDTO.class)
    public abstract Collection<AdminUserRespDTO> usersToAdminRespDTOs(Collection<User> users);
}
