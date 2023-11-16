package com.asia.asia.repositories;

import com.asia.asia.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    Iterable<TodoItem> findByUserId(Long userId);
}
