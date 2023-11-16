package com.asia.asia.services;

import com.asia.asia.entities.TodoItem;

import java.util.List;
import java.util.Optional;


public interface TodoItemService {


    Optional<TodoItem> getById(Long id);

    Iterable<TodoItem> getAll();

    Iterable<TodoItem> getAllFromUser(Long id);

    TodoItem save(TodoItem todoItem);

    void delete(TodoItem todoItem);

    String exportToXml(List<TodoItem> todoItems);

    String exportToJson(List<TodoItem> todoItems);
}

