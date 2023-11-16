package com.asia.asia.controllers;

import com.asia.asia.entities.TodoItem;
import com.asia.asia.entities.User;
import com.asia.asia.services.JwtService;
import com.asia.asia.services.TodoItemService;
import com.asia.asia.services.UserService;
import com.asia.asia.services.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Getter
@Setter
@Controller
public class TodoFormController {

    @Autowired
    private TodoItemService todoItemService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model, Authentication authentication) {
        Long userId = jwtService.extractLoggedInUserId();
        Optional<User> loggedUser = userService.getUserById(userId);
        if (loggedUser.isPresent()){
            System.out.println(loggedUser.get().toString());
            TodoItem item = new TodoItem();
            item.setDescription(todoItem.getDescription());
            item.setIsComplete(todoItem.getIsComplete());
            item.setTillWhen(todoItem.getTillWhen());
            item.setUser(loggedUser.get());
            todoItemService.save(item);
            return "redirect:/";
        }

        return "redirect:/auth/signin";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            Long loggedInUserId = jwtService.extractLoggedInUserId();
            if (loggedInUserId.equals(todoItem.getUser().getId())) {
                todoItemService.delete(todoItem);
                return "redirect:/";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);

        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            Long loggedInUserId = jwtService.extractLoggedInUserId();

            if (loggedInUserId.equals(todoItem.getUser().getId())) {
                // User is the owner, proceed with showing the update form
                model.addAttribute("todo", todoItem);
                return "edit-todo-item";
            } else {
                return "redirect:/user-todos";
            }
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model, Authentication authentication) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            Long loggedInUserId = jwtService.extractLoggedInUserId();
            if (loggedInUserId.equals(existingTodoItem.getUser().getId())) {
                existingTodoItem.setIsComplete(todoItem.getIsComplete());
                existingTodoItem.setDescription(todoItem.getDescription());
                todoItemService.save(existingTodoItem);
                return "redirect:/";
            } else {

                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }


}