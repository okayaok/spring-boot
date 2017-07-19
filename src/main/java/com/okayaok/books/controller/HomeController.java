package com.okayaok.books.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主页
 *
 * @author hang_xiao
 *         2017/7/14.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * 图书管理系统主页
     *
     * @return 主页视图路径
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 跳转到404页面
     *
     * @return 404页面
     */
    @RequestMapping(value = "/error/404", method = RequestMethod.GET)
    public String viewNotFount() {
        return "/error/404";
    }

    /**
     * 跳转至无权限页面
     *
     * @return 403页面
     */
    @RequestMapping(value = "/error/403", method = RequestMethod.GET)
    public String unAuthentication() {
        return "/error/403";
    }
}
