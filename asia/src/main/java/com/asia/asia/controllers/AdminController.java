package com.asia.asia.controllers;

import com.asia.asia.services.TodoItemService;
import com.asia.asia.services.UserService;
import com.asia.asia.services.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private TodoItemService todoItemService;
    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserService userService;

//    @GetMapping("/admin")
//    public ModelAndView adminHome(RedirectAttributes redirectAttributes) {
//        ModelAndView modelAndView = new ModelAndView("admin-home");
//        Long adminId = jwtService.extractLoggedInUserId();
//        System.out.println(adminId);
//        modelAndView.addObject("todoItems", todoItemService.getAll());
//      //  modelAndView.addObject("users", userService.getAllUsers()); // Add whatever admin-specific data you need
//        return modelAndView;
//    }


        @Autowired
        public AdminController(UserService userService) {
            this.userService = userService;
        }

//        @GetMapping("/admin/assign-role")
//        public String showAssignRoleForm() {
//            return "assign-role-form";
//        }
//
//        @PostMapping("/admin/assign-role")
//        public String assignAdminRole(@RequestParam String username) {
//            userService.assignAdminRole(username);
//            return "redirect:/admin";
//        }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/assign-role")
    public String assignAdminRole(@RequestParam String username) {
        userService.assignAdminRole(username);
        return "redirect:/admin";
    }
    }



