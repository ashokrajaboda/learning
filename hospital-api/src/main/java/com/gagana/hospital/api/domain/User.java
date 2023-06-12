package com.gagana.hospital.api.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Accessors(chain = true)
@ToString
@Table("t_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6665052093292574249L;
    @Id
    @Column("user_id")
    private Long userId;
    @NotBlank(message = "Username should not be empty")
    private String username;
    private String password;
    private Boolean enabled;
    private List<Authorities> roles;
    //@OneToOne()
    //private UserDetails userDetails;
}
