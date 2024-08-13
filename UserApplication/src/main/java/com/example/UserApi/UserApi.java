package com.example.UserApi;

import com.example.UserDto.CreateUserRequestDto;
import com.example.UserDto.UserDto;
import com.example.UserEntity.UserEntity;
import com.example.UserService.UserService;
import com.example.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody @Valid CreateUserRequestDto userRequest) throws JsonProcessingException {
        userService.create(userRequest);
    }

    @GetMapping("/user/{userId}")
    public UserEntity getUser(@PathVariable("userId") int userId) throws Exception {
       UserEntity user = userService.get(userId);
        return user;
    }
}
