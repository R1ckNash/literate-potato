package com.springboot.bozon.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author mialyshev
 */

@Entity
@Table(name = "comments")
@Data
public class Comment extends BaseEntity{

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Comment comment = (Comment)obj;

        return comment.rating == this.rating && comment.comment.equals(this.comment);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + Math.toIntExact(super.getId());
        return result;
    }

}
