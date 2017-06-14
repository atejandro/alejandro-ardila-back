package com.atejandro.examples.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by atejandro on 12/06/17.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sure_name", nullable = false)
    private String sureName;

    @NaturalId
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Cube> cubes = new HashSet<>();

    public User() {
    }

    public User(String name, String sureName, String email) {
        this.name = name;
        this.sureName = sureName;
        this.email = email;
    }

    public boolean hasCompulsoryFields(){
        return this.email != null && this.name != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Cube> getCubes() {
        return cubes;
    }

    public void setCubes(Set<Cube> cubes) {
        this.cubes = cubes;
    }
}
