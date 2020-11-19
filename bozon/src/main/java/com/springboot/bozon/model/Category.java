package com.springboot.bozon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author mialyshev
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@Data
public class Category extends BaseEntity {

    @Column(name = "name")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 15)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Post> posts;

}
