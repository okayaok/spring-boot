package com.okayaok.books.controller;

import com.okayaok.books.domain.User;
import com.okayaok.books.repository.UsersRepository;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import io.jstack.sendcloud4j.mail.Substitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Controller
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    private static final String API_KEY = "tJ6HVwc20yrD97Z6";

    private static final String API_USER = "okayaok_test_OM8pcl";

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
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
    public String register(User user, Model model) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepository.save(user);
            model.addAttribute("messages", "success");
        } catch (Exception e) {
            model.addAttribute("messages", "failure");
        }

        return "login";
    }

    /**
     * 发送邮件
     * @param email 邮箱
     * @param model
     * @return 登录路径
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public String sendEmail(String email, Model model) {
        SendCloud sendCloud = SendCloud.createWebApi(API_USER, API_KEY);
        Email template = Email.template("books_management_system_reset_password")
                .from("service@sendcloud.im")
                .substitutionVars(Substitution.sub()
                        .set("name", "okayaok")
                        .set("time", SimpleDateFormat.getDateTimeInstance().format(new Date()))
                        .set("resetPasswordUrl", "http://localhost:8080/resetPassword"))
                .to(email);
        Result result = sendCloud.mail().send(template);
        model.addAttribute("messages", result.getMessage());
        return "redirect: /login";
    }
}
