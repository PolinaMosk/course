package com.trkpo.course.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "blogUser")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;
    @ManyToMany
    private List<User> favourites;
    private String email;

    public User(String name, String email, Picture picture) {
        this.name = name;
        this.picture = picture;
    }

    public User() {
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public List<User> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<User> favourites) {
        this.favourites = favourites;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
