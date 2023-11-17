package com.asia.asia.controllers;

import com.asia.asia.entities.Priority;
import com.asia.asia.entities.Role;
import com.asia.asia.entities.TodoItem;
import com.asia.asia.services.JwtService;
import com.asia.asia.services.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TodoItemService todoItemService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/")
    public ModelAndView index(RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("index");
        Long userId = jwtService.extractLoggedInUserId();
        Role userRole = jwtService.extractLoggedInUserRole();
        modelAndView.addObject("isAdmin", userRole.equals(Role.ADMIN));
        modelAndView.addObject("todoItems", todoItemService.getAllFromUser(userId));
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView indexAdmin(RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("index-admin");
        modelAndView.addObject("todoItems", todoItemService.getAll());
        return modelAndView;
    }

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<String> exportTodo(
            @RequestParam(name = "format", defaultValue = "json") String format,
            RedirectAttributes redirectAttributes) {

        Long userId = jwtService.extractLoggedInUserId();
        List<TodoItem> todoItems = (List<TodoItem>) todoItemService.getAllFromUser(userId);

        if ("xml".equalsIgnoreCase(format)) {
            String xmlData = todoItemService.exportToXml(todoItems);
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=todo-export.xml")
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlData);
        } else {
            String jsonData = todoItemService.exportToJson(todoItems);
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=todo-export.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonData);
        }
    }

    @GetMapping("/exportHigh")
    @ResponseBody
    public ResponseEntity<String> exportTodoHigh(
            RedirectAttributes redirectAttributes) {

        Long userId = jwtService.extractLoggedInUserId();
        Iterable<TodoItem> todoItems = todoItemService.findByPriority(Priority.HIGH, userId);

        String xmlData = todoItemService.exportToXml((List<TodoItem>) todoItems);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=todoHigh-export.xml")
                .contentType(MediaType.APPLICATION_XML)
                .body(xmlData);
    }

    @GetMapping("/admin/export")
    @ResponseBody
    public ResponseEntity<String> exportTodoAdmin(
            @RequestParam(name = "format", defaultValue = "json") String format,
            RedirectAttributes redirectAttributes) {

        List<TodoItem> todoItems = (List<TodoItem>) todoItemService.getAll();

        if ("xml".equalsIgnoreCase(format)) {
            String xmlData = todoItemService.exportToXml(todoItems);
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=todo-export-admin.xml")
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlData);
        } else {
            String jsonData = todoItemService.exportToJson(todoItems);
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=todo-export-admin.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonData);
        }
    }
}
