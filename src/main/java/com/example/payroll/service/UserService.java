package com.example.payroll.service;

import com.example.payroll.persistence.dao.UserRepository;
import com.example.payroll.persistence.model.User;
import com.example.payroll.web.dto.UserDto;
import com.example.payroll.web.error.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserDto userDto) {
        User user = mapToUser(userDto);
        return userRepository.save(user);
    }

    public User updateUser(UserDto userDto) {
        return userRepository.findById(userDto.getId()).map(user -> {
                user.setName(userDto.getName());
                user.setEmail(userDto.getEmail());
                user.setPassword(userDto.getPassword());
            return user;
        }).orElseThrow(() -> new UserNotFoundException(userDto.getId()));
    }

    private User mapToUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }
}
