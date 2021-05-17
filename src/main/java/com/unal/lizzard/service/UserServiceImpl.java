package com.unal.lizzard.service;

import com.unal.lizzard.model.Role;
import com.unal.lizzard.model.User;
import com.unal.lizzard.repository.UserRepository;
import com.unal.lizzard.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    //Encriptacion contraseña
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto){
        User user = new User(
                registrationDto.getFirstname(),
                registrationDto.getLastname(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    /* codigo Lab4 eliminado por el lab 5
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(
                registrationDto.getFirstname(),
                registrationDto.getLastname(),
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException("Usuario y/o contraseña incorrecta.");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword()
                , mapRolesToAuthorities(user.getRoles()));
    }



    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }



}
