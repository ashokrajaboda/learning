package com.gagana.hospital.api.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("t_user_details")
@Accessors(chain = true)
public class UserDetails implements Serializable {

    private static final long serialVersionUID = -6811517218638711305L;
    @Id
    @Column("user_id")
    private Long userId;
    @NotBlank(message = "Firstname should not be empty")
    private String firstName;
    @NotBlank(message = "Lastname should not be empty")
    private String lastName;

    private String email;
    @NotBlank(message = "Address should not be empty")
    private String address;
    @NotBlank(message = "Mobile No should not be empty")
    private String mobileNo;
}
