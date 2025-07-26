package com.intern.userservice.Repository;

import com.intern.userservice.Dto.UserDto;
import com.intern.userservice.Models.UserModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDB extends JpaRepository<UserModel, Integer> {

    UserModel findByEmail(String email);
}
