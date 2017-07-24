package com.okayaok.books.controller.user;

import com.okayaok.books.repository.UsersRepository;
import com.okayaok.books.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String index(Model model, @PageableDefault Pageable pageable) {
        Page<User> users = usersRepository.findAll(pageable);
        model.addAttribute("users", users);
        return "user/index";
    }

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 跳转路径
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user) {
        usersRepository.save(user);
        return "redirect:/users";
    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 跳转路径
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(User user) {
        usersRepository.save(user);
        return "redirect:/users";
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 跳转路径
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        usersRepository.delete(id);
        return "redirect:/users";
    }
}
