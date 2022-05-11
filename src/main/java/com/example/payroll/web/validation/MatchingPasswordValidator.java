package com.example.payroll.web.validation;

import com.example.payroll.web.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPassword, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = (UserDto) obj;
        return Objects.equals(userDto.getPassword(), userDto.getMatchingPassword());
    }
}
