package com.unal.lizzard.service;

import com.unal.lizzard.model.User;
import com.unal.lizzard.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
}
