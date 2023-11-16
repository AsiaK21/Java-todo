package com.asia.asia.controllers;

import com.asia.asia.entities.TodoItem;
import com.asia.asia.services.TodoItemService;
import com.asia.asia.services.impl.JwtServiceImpl;
import com.asia.asia.services.impl.TodoItemServiceImpl;
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
    private JwtServiceImpl jwtService;

    @GetMapping("/")
    public ModelAndView index(RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("index");
        Long userId = jwtService.extractLoggedInUserId();
        System.out.println(userId);
        modelAndView.addObject("todoItems", todoItemService.getAllFromUser(userId));
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
            // Export as XML
            String xmlData = todoItemService.exportToXml(todoItems);
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=todo-export.xml")
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlData);
        } else {
            // Default to JSON export
            String jsonData = todoItemService.exportToJson(todoItems);
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=todo-export.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonData);
        }
    }
}

