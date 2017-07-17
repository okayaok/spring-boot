package com.okayaok.books.controller;

import com.okayaok.books.domain.User;
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

    @RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
    public String resetPassword(User user, Model model) {

        return "login";
    }

}
