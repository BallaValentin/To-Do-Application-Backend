package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.services.EmailService;
import edu.bbte.idde.bvim2209.spring.backend.services.UserService;
import edu.bbte.idde.bvim2209.spring.backend.util.JwtUtil;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import edu.bbte.idde.bvim2209.spring.web.dto.request.NewPasswordRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.PasswordResetEmailDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserLoginReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.request.UserRegisterReqDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.RefreshTokenResponse;
import edu.bbte.idde.bvim2209.spring.web.dto.response.UserResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
@Slf4j
public class AuthController {
    JwtUtil jwtUtil = new JwtUtil();
    UserMapper userMapper;
    UserService userService;
    EmailService emailService;

    @Autowired
    public AuthController(UserMapper userMapper, UserService userService, EmailService emailService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRegisterReqDTO requestDTO) {
        User user = userMapper.registerDTOToModel(requestDTO);
        user.setRole("user");
        userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody UserLoginReqDTO requestDTO,
                                                     HttpServletResponse response) {
        User loginData = userMapper.loginDTOToModel(requestDTO);
        Boolean rememberMe = requestDTO.getRememberMe();
        User user = userService.loginUser(loginData);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        String[] userData = {user.getUsername(), user.getFullname(), user.getEmail()};
        String subject = String.join("|", userData);

        String accessToken = jwtUtil.generateAccessToken(subject);
        userResponseDTO.setAccessToken(accessToken);

        if (rememberMe) {
            String refreshToken = jwtUtil.generateRefreshToken(subject);
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30);
            refreshTokenCookie.setAttribute("SameSite", "Strict");
            response.addCookie(refreshTokenCookie);
        }

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/logout")
    public void logoutUser(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        response.addCookie(refreshTokenCookie);
    }


    @GetMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                String refreshToken = cookie.getValue();
                Boolean isValidToken = jwtUtil.validateRefreshToken(refreshToken);
                if (isValidToken) {
                    String subject = jwtUtil.extractRefreshToken(refreshToken);
                    String accessToken = jwtUtil.generateAccessToken(subject);
                    RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
                    refreshTokenResponse.setAccessToken(accessToken);
                    return ResponseEntity.ok(refreshTokenResponse);
                }
            }
        }
        throw new UnauthorizedException("Invalid refresh token");
    }

    @PostMapping("forgot-password")
    public String forgotPassword(@Valid @RequestBody PasswordResetEmailDTO passwordResetRequestDTO) {
        String email = passwordResetRequestDTO.getEmail();
        String token = jwtUtil.generateAccessToken(email);
        try {
            emailService.sendPasswordResetEmail(email, token);
            return "Password reset email sent";
        } catch (MessagingException exception) {
            return "Error while sending password reset email";
        }
    }

    @PostMapping("change-password")
    public void changePassword(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody NewPasswordRequestDTO newPasswordRequestDTO) {
        String token = authorizationHeader.substring(7);
        String email = jwtUtil.extractAccessToken(token);
        String newPassword = newPasswordRequestDTO.getNewPassword();
        User user = userService.getByEmail(email);
        userService.updatePassword(user, newPassword);
    }
}
