package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.services.UserService;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserLoginReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserRegisterReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.UserResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:5173")
public class AuthController {
    UserMapper userMapper;
    UserService userService;

    @Autowired
    public AuthController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRegisterReqDTO requestDTO) {
        User user = userMapper.registerDTOToModel(requestDTO);
        user.setRole("user");
        userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody UserLoginReqDTO requestDTO) {
        User user = userMapper.loginDTOToModel(requestDTO);
        Boolean rememberMe = requestDTO.getRememberMe();
        String jwtToken = userService.loginUser(user, rememberMe);
        UserResponseDTO userResponseDTO = userMapper.userToResponseDTO(user);
        userResponseDTO.setJwtToken(jwtToken);
        return ResponseEntity.ok(userResponseDTO);
    }
}
