package com.example.payroll.web.assembler;

import com.example.payroll.web.controller.UserRestController;
import com.example.payroll.persistence.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserRestController.class).getUserById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserRestController.class).getAllUsers()).withRel("users"));
    }
}
