package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.services.UserService;
import edu.bbte.idde.bvim2209.spring.backend.util.JwtUtil;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserLoginReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserRegisterReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.RefreshTokenResponse;
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
    JwtUtil jwtUtil = new JwtUtil();
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
        userService.loginUser(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        String accessToken = jwtUtil.generateAccessToken(user.getUsername(),
                user.getFullname(),
                user.getRole());
        userResponseDTO.setAccessToken(accessToken);

        if (rememberMe) {
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(),
                    user.getFullname(),
                    user.getRole());
            userResponseDTO.setRefreshToken(refreshToken);
        }

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Boolean isValidToken = jwtUtil.validateRefreshToken(token);
            if (isValidToken) {
                String username = jwtUtil.extractUsername(token);
                String fullname = jwtUtil.extractFullname(token);
                String role = jwtUtil.extractRole(token);

                String accessToken = jwtUtil.generateAccessToken(username, fullname, role);
                RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
                refreshTokenResponse.setAccessToken(accessToken);
                return ResponseEntity.ok(refreshTokenResponse);
            } else {
                throw new UnauthorizedException("Invalid refresh token");
            }
        } else {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }
}
