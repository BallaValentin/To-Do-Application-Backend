package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.services.UserService;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserLoginReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserRegisterReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.UserResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.UserMapper;
import edu.bbte.idde.bvim2209.spring.web.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173")
@Slf4j
public class UserController {
    UserMapper userMapper;
    UserService userService;
    JwtUtil jwtUtil;

    @Autowired
    public UserController(UserMapper userMapper, UserService userService) {
        jwtUtil = new JwtUtil();
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
        User user2 = userService.loginUser(user);
        UserResponseDTO userResponseDTO = userMapper.userToResponseDTO(user2);
        String jwtToken;
        if (requestDTO.getRememberMe()) {
            jwtToken = jwtUtil.generateToken(
                    user2.getUsername(), user2.getFullname(), user2.getRole(),  60000 * 1440 * 365L);
        } else {
            jwtToken = jwtUtil.generateToken(
                    user2.getUsername(), user2.getFullname(), user2.getRole(), 600000L);
        }
        userResponseDTO.setJwtToken(jwtToken);
        return ResponseEntity.ok(userResponseDTO);
    }
}
