package com.trkpo.course.entity;

import javax.persistence.*;

@Entity(name = "Credentials")
public class Credentials {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Credentials() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
