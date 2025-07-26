package com.intern.userservice.Service;

import com.intern.userservice.Dto.UserDto;
import com.intern.userservice.Models.UserModel;
import com.intern.userservice.Repository.UserDB;
import com.intern.userservice.Token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;


import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDB userDB;
    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<String> createUser(UserDto userDto) {
        UserModel userModel = new UserModel();
        userModel.setEmail(userDto.getEmail());

        String encodedPassword = pwdEncoder.encode(userDto.getPassword());
        userModel.setPassword(encodedPassword);

        userDB.save(userModel);
        return new ResponseEntity<String>("sucess", HttpStatus.CREATED);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userDB.findByEmail(email);
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
