package com.asia.asia;

//package com.asia.asia.repository;

import com.asia.asia.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
