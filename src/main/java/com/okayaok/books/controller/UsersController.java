package com.okayaok.books.controller;

import com.okayaok.books.repository.UsersRepository;
import com.okayaok.books.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 获取用户列表
     */
    public void list() {
        List<User> users = usersRepository.findAll();
    }
}
