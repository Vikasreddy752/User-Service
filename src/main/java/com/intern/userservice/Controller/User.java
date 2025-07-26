package com.intern.userservice.Controller;

import com.intern.userservice.Dto.UserDto;
import com.intern.userservice.Service.UserService;
import com.intern.userservice.Token.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;

@RestController
@RequestMapping
@Slf4j
public class User {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/signup")
    public String signup(@RequestBody UserDto userDto) {
        ResponseEntity<String> response= userService.createUser(userDto);
        if(response.getStatusCode()== HttpStatus.CREATED){
            return "successfully created";
        }
        return "failed";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(userDto.getEmail());
//log.info(token);
            return ResponseEntity.ok(token);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
    @GetMapping("/welcome")
    public String welcome() {
        //log.info("Welcome to user service");
        return "welcome we are Authenticated by JWT token";
    }
}
