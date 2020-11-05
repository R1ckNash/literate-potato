package com.springboot.bozon.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author mialyshev
 */

@Entity
@Table(name = "categories")
@Data
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<Post> posts;

}
