package com.PRM392.prm392.controller;

import com.PRM392.prm392.entity.User;
import com.PRM392.prm392.repository.UserRepository;
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
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/customer/sign_up")
    public ResponseEntity<ResponseData<?>> createCustomer (@RequestBody CustomerCreateRequet request){
        try {
            boolean isCreated = userService.isExist(request.getUserName());
            if (!isCreated) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                        .body(new ResponseData<>("Account already exists", null));
            }
            User createdUser = userService.createCustomer(request);
            return ResponseEntity.ok(new ResponseData<>("Customer created successfully", createdUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseData<?>> login (@RequestParam String username, @RequestParam String password){
        try {
            User user = userRepository.findByUserName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseData<>("User not found", null));
            } else if(!userService.login(username, password)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseData<>("Wrong password", null));
            }
            return ResponseEntity.ok(new ResponseData<>("Login successful", user));
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
