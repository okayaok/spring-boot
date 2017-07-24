package com.okayaok.books.controller.role;

import com.okayaok.books.domain.Role;
import com.okayaok.books.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author okayaok
 *         2017/7/16.
 */
@Controller
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    /**
     * 获取所有角色信息
     *
     * @param model    存放角色信息
     * @param pageable 分页
     * @return 页面路径
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @PageableDefault Pageable pageable) {
        Page<Role> roles = rolesRepository.findAll(pageable);
        model.addAttribute("roles", roles);
        return "role/index";
    }

    /**
     * 保存角色
     *
     * @param role 角色
     * @return 跳转路径
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Role role) {
        rolesRepository.save(role);
        return "redirect:/roles";
    }

    /**
     * 更新角色名称
     *
     * @param role 角色
     * @return 跳转路径
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Role role) {
        rolesRepository.save(role);
        return "redirect:/roles";
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 跳转路径
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        rolesRepository.delete(id);
        return "redirect:/roles";
    }
}
