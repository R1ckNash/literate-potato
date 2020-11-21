package com.springboot.bozon.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author mialyshev
 */

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}