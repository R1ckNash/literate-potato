package com.springboot.bozon.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * @author mialyshev
 */

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToOne(mappedBy = "users", fetch = FetchType.EAGER)
    private Sale sale;
}
