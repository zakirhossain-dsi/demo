package com.example.demo.entity;

import javax.persistence.*;

import com.example.demo.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "person")
public class PersonEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, length = 20)
  protected long personId;

  @Column private String firstName;
  @Column private String lastName;
  @Column private String email;
  @Column private String profileImageUrl;
}
