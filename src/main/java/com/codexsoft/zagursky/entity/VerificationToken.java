package com.codexsoft.zagursky.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
@Data
@Entity
 public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;

 private String token;

 @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
 @JoinColumn(nullable = false, name = "user_id")
 private User user;



 }