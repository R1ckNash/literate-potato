package com.springboot.bozon.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author mialyshev
 */

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> users;

    @Override
    public String toString() {
        return name;
    }
}