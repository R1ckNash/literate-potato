package com.springboot.bozon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author mialyshev
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posts")
@Data
public class Post extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "picture_url")
    private String url;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name="user_id")
    private User seller;

    @OneToOne (mappedBy="post",  fetch = FetchType.EAGER)
    private Sale sale;
}
