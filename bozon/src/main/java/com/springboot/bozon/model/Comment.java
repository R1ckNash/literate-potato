package com.springboot.bozon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author mialyshev
 */

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    @NotNull
    @DecimalMin("1")
    @DecimalMax("5")
    private int rating;

    //whom we evaluate
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rated_user_id")
    private User rated_user;

    //who evaluates
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "evaluator_user_id")
    private User evaluator_user;
}
