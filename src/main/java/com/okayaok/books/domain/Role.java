package com.okayaok.books.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author hang_xiao
 *         2017/7/14.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "role_name")
    private String RoleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

}
