package com.springboot.bozon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


/**
 * @author mialyshev
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username")
    @NotEmpty(message = "username should not be empty")
    @Size(min = 2, max = 15)
    private String username;

    @Column(name = "first_name")
    @NotEmpty(message = "first_name should not be empty")
    @Size(min = 2, max = 15)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "last_name should not be empty")
    @Size(min = 2, max = 15)
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "password should not be empty")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "phone_number")
    @NotEmpty(message = "phone_number should not be empty")
    @Size(min = 11, max = 12, message = "номер должен быть в диапазоне от 11 до 12")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Sale> sales;

    @OneToMany(mappedBy = "evaluator_user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "rated_user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> rating;
}
