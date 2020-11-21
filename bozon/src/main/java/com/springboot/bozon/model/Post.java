package com.springboot.bozon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author mialyshev
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posts")
@Data
public class Post extends BaseEntity {

    @Column(name = "name")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 15)
    private String name;

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "picture_url")
    private String url;

    @Column(name = "description")
    @NotEmpty(message = "description should not be empty")
    @Size(min = 2, max = 15)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User seller;

    @OneToOne(mappedBy = "post", fetch = FetchType.EAGER)
    private Sale sale;
}
