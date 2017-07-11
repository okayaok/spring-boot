package com.okayaok.books.controller;

import com.okayaok.books.repository.UsersRepository;
import com.okayaok.books.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user", new User());
        System.out.println("开始跳转至登陆页面");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user) {
        boolean isExist = usersRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (isExist) {
            System.out.println("登陆成功");
            return "redirect:/users";
        } else {
            return "redirect:/login";
        }
    }
}