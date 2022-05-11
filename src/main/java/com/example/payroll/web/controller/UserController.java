package com.example.payroll.web.controller;

import com.example.payroll.service.UserService;
import com.example.payroll.persistence.model.User;
import com.example.payroll.web.assembler.UserModelAssembler;
import com.example.payroll.persistence.dao.UserRepository;
import com.example.payroll.web.error.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserModelAssembler userAssembler;

    public UserController(UserService userService, UserRepository repository, UserModelAssembler assembler) {
        this.userService = userService;
        this.userRepository = repository;
        this.userAssembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> getAllUsers() {

        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {

        User user = userRepository.save(newUser);
        log.info("User added " + user);
        return user;
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return userAssembler.toModel(user);

    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setRoles(newUser.getRoles());
            user.setBalance(newUser.getBalance());
            return userRepository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return userRepository.save(newUser);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("")
    public void deleteAllUser() {
        userRepository.deleteAll();
    }
}
