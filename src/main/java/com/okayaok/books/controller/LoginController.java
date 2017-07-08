package com.okayaok.books.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@RestController
@RequestMapping("/")
public class LoginController {

    public void login() {
       System.out.println("开始跳转至登陆页面");
    }

    public void login(String username, String password) {
        System.out.println("登陆成功");
    }
}
