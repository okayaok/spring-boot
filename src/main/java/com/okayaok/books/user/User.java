package com.okayaok.books.user;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Entity
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;
}
