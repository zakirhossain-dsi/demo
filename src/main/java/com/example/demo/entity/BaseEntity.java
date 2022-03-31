package com.example.demo.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {

  @Column(updatable = false)
  protected Date createDate = new Date();

  @Column protected Date updateDate = new Date();

  @Column(updatable = false)
  protected String createdBy = "zakir@gmail.com";

  @Column protected String updatedBy = "zakir@gmail.com";
}
