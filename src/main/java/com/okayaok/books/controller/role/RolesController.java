package com.okayaok.books.controller.role;

import com.okayaok.books.domain.Role;
import com.okayaok.books.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author okayaok
 * 2017/7/16.
 */
@Controller
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, Pageable pageable) {
        Page<Role> roles = rolesRepository.findAll(pageable);
        model.addAttribute("roles", roles);
        return "role/index";
    }
}
