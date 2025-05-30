package com.SRS.SRS.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDto {

    private Long id;

    @NotNull(message = "username required")
    private String username;
    @Email(message = "Invalid email type")
    @NotNull(message = "Email is Required")
    private String email;
    private String password;
    private String role;
}
