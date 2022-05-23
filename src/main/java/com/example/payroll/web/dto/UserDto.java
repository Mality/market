package com.example.payroll.web.dto;

import com.example.payroll.web.validation.MatchingPassword;
import com.example.payroll.web.validation.ValidEmail;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@MatchingPassword
public class UserDto {

    @NotBlank(message = "Name should not be blank")
    @Length(min = 4, max = 30, message = "Length should be from 4 to 30 characters")
    private String name;

    @ValidEmail
    private String email;

    @NotBlank
    @Length(min = 5, max = 30, message = "Password should be from 5 to 30 characters")
    private String password;

    private String matchingPassword;

    public UserDto() {
    }

    public UserDto(String name, String email, String password, String matchingPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(password, userDto.password) && Objects.equals(matchingPassword, userDto.matchingPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, matchingPassword);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                '}';
    }
}
