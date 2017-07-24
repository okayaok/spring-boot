package com.okayaok.books.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * 使用@JoinTable标签的name属性注解第三方表名称,
     * 使用joinColumns属性来注解当前实体类在第三方表中的字段名称并指向该对象,此处当前对象为User,字段为"user_id"
     * 使用inverseJoinColumns属性来注解当前实体类的引用对象在第三方表中的字段名称并指向被引用对象表,此处引用对象为Role,字段为"role_id"
     */
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;*/

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(this.getRole().getRoleName()));
        return auth;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
