package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.services.UserService;
import edu.bbte.idde.bvim2209.spring.web.dto.response.AdminUserRespDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:5173")
public class AdminController {
    UserMapper userMapper;
    UserService userService;

    @Autowired
    public AdminController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<AdminUserRespDTO> getAllUsers(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String jwtToken = authorizationHeader.substring(7);
        Collection<User> users = userService.getAllUsers(jwtToken);
        return userMapper.usersToAdminRespDTOs(users);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long id,
                           @RequestHeader("Authorization") String authorizationHeader
    ) {
        String jwtToken = authorizationHeader.substring(7);
        User user = userService.getById(id);
        userService.deleteUser(user, jwtToken);
    }
}
