package com.springboot.bozon.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author mialyshev
 */

@Entity
@Table(name = "sales")
@Data
public class Sale extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "buyer_id")
    private User buyer;
}
