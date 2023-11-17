package com.asia.asia.controllers;

import com.asia.asia.entities.TodoItem;
import com.asia.asia.services.TodoItemService;
import com.asia.asia.services.UserService;
import com.asia.asia.services.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TodoItemService todoItemService;
    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserService userService;

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            todoItemService.delete(todoItem);
            return "redirect:/admin";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);

        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            model.addAttribute("todo", todoItem);
            return "edit-todo-item-admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model, Authentication authentication) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            existingTodoItem.setIsComplete(todoItem.getIsComplete());
            existingTodoItem.setDescription(todoItem.getDescription());
            todoItemService.save(existingTodoItem);
            return "redirect:/admin";
        } else {

            return "redirect:/admin";
        }
    }
}
