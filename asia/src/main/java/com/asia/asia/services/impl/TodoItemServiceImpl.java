package com.asia.asia.services.impl;

import com.asia.asia.entities.Priority;
import com.asia.asia.entities.TodoItem;
import com.asia.asia.repositories.TodoItemRepository;
import com.asia.asia.services.TodoItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public Optional<TodoItem> getById(Long id) {
        return todoItemRepository.findById(id);
    }

    public Iterable<TodoItem> getAll() {
        return todoItemRepository.findAll();
    }

    // @Override
    public Iterable<TodoItem> getAllFromUser(Long id) {
        return todoItemRepository.findByUserId(id);
    }

    public TodoItem save(TodoItem todoItem) {
        if (todoItem.getId() == null) {
            todoItem.setCreatedAt(LocalDateTime.now());
        }
        todoItem.setUpdatedAt(LocalDateTime.now());
        return todoItemRepository.save(todoItem);
    }

    public void delete(TodoItem todoItem) {
        todoItemRepository.delete(todoItem);
    }

    public String exportToXml(List<TodoItem> todoItems) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(todoItems);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String exportToJson(List<TodoItem> todoItems) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(todoItems);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Iterable<TodoItem> findByPriority(Priority high, Long id) {
        return todoItemRepository.findByPriorityAndUserId(high, id);
    }
}
