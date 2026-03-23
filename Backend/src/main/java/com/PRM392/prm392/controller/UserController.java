package com.PRM392.prm392.controller;

import com.PRM392.prm392.entity.User;
import com.PRM392.prm392.request.create.User.CustomerCreateRequet;
import com.PRM392.prm392.response.ResponseData;
import com.PRM392.prm392.service.Interface.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public ResponseEntity<ResponseData<?>> createCustomer (@RequestBody CustomerCreateRequet request){
        try {
            User createdUser = userService.createCustomer(request);
            return ResponseEntity.ok(new ResponseData<>("Customer created successfully", createdUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }


    @GetMapping
    public ResponseEntity<ResponseData<?>> getAllCustomers() {
        try {
            List<User> users =userService.getAllUser();
            return ResponseEntity.ok(new ResponseData<>("All customers successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{userID}")
    public ResponseEntity<ResponseData<?>> getUser(@PathVariable int userID) {
        try {
            User user = userService.getUserById(userID);
            return ResponseEntity.ok(new ResponseData<>("User successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

}
