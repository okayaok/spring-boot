package com.okayaok.books.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
