package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "CourseRating")
public class CourseRatingEntity extends BaseEntity {

    @EmbeddedId
    private CourseRatingKey courseRatingKey;

    public CourseRatingEntity(CourseRatingKey courseRatingKey){
        this.courseRatingKey = courseRatingKey;
    }

    @Column
    private int rating;
}
