package com.okayaok.books.controller;

import com.okayaok.books.domain.User;
import com.okayaok.books.repository.UsersRepository;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import io.jstack.sendcloud4j.mail.Substitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Controller
public class LoginController {

    @Autowired
    private final UsersRepository usersRepository;

    /**
     * SendCloud邮件发送的密钥
     */
    private static final String API_KEY = "tJ6HVwc20yrD97Z6";

    /**
     * SendCloud邮件发送的测试用户
     */
    private static final String API_USER = "okayaok_test_OM8pcl";

    /**
     * 重置密码的请求路径
     */
    private static final String RESET_PASSWORD_URL = "http://localhost:8080/resetPassword";

    public LoginController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword() {
        return "login";
    }

    /**
     * 用户注册
     *
     * @param user 用户基本信息
     * @param attr 跳转属性
     * @return 登录路径
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user, RedirectAttributes attr) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepository.save(user);
            attr.addFlashAttribute("messages", "success");
        } catch (Exception e) {
            attr.addFlashAttribute("messages", "failure");
        }

        return "redirect:/login";
    }

    /**
     * 发送邮件
     *
     * @param email 邮箱
     * @param attr  跳转属性
     * @return 登录路径
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public String sendEmail(String email, RedirectAttributes attr) {
        //根据用户邮箱获取用户信息
        User user = usersRepository.findByEmail(email);
        //初始化SendCloud信息
        SendCloud sendCloud = SendCloud.createWebApi(API_USER, API_KEY);
        //加载邮件的模板
        Email template = Email.template("books_management_system_reset_password")
                .from("service@sendcloud.im")
                //替换模板中的变量
                .substitutionVars(Substitution.sub()
                        .set("name", user.getUsername())
                        .set("time", SimpleDateFormat.getDateTimeInstance().format(new Date()))
                        .set("resetPasswordUrl", RESET_PASSWORD_URL))
                //设置发送的邮箱
                .to(email);
        //发送邮件并且记录邮件发送状态
        Result result = sendCloud.mail().send(template);
        //返回邮件状态码
        attr.addFlashAttribute("messages", result.getStatusCode());
        return "redirect:/login";
    }
}
