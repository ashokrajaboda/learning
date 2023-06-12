package com.gagana.hospital.api.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -6516386748829363591L;
    @NotBlank(message = "username should not be blank")
    private String username;
    @NotBlank(message = "password should not blank")
    @Size(min = 8, max = 255)
    private String password;
}
