package com.okayaok.books.controller;

import com.okayaok.books.domain.User;
import com.okayaok.books.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Controller
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
    public String resetPassword(User user, Model model) {
        return "login";
    }

    /**
     * 用户注册
     *
     * @param user 用户基本信息
     * @return 成功页面
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return "login";
    }

}
