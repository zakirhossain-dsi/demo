package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 20)
    protected long Id;

    @Column
    protected Date createDate;

    @Column
    protected Date updateDate;

    @Column
    protected String createdBy;

    @Column
    protected String updatedBy;
}
