package com.asia.asia.services;

import com.asia.asia.entities.TodoItem;

import java.util.Optional;


public interface TodoItemService {


    Optional<TodoItem> getById(Long id);

    Iterable<TodoItem> getAll();

    TodoItem save(TodoItem todoItem);

    void delete(TodoItem todoItem);
}

