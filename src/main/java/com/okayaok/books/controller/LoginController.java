package com.okayaok.books.controller;

import com.okayaok.books.repository.UsersRepository;
import com.okayaok.books.domain.User;
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
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(User user) {
        boolean isExist = usersRepository.existsByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (isExist) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
}
