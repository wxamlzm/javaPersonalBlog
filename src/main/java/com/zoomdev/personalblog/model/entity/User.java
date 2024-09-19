package com.zoomdev.personalblog.model.entity;


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=IDENTITY)
    private long id;

    @Column(name="username")
    private String name;

    @Column(name="password")
    private String password;
}
