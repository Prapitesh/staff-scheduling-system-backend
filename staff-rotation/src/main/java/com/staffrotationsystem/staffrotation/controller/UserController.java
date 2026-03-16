//package com.staffrotationsystem.staffrotation.controller;
//
//import com.staffrotationsystem.staffrotation.entity.User;
//import com.staffrotationsystem.staffrotation.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    @PostMapping("/save-user")
//    public ResponseEntity<String> saveUser(@RequestBody User user) {
//         try {
//             userService.addUser(user);
//             return new ResponseEntity<>("User has been saved successfully", HttpStatus.OK);
//         }
//         catch (Exception e) {
//             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//         }
//    }
//}
