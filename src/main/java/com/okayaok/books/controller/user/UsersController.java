package com.okayaok.books.controller.user;

import com.okayaok.books.repository.UsersRepository;
import com.okayaok.books.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 获取用户列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, Pageable pageable) {
        Page<User> users = usersRepository.findAll(pageable);
        model.addAttribute("users", users);
        return "user/index";
    }
}
