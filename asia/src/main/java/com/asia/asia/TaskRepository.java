package com.asia.asia;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends JpaRepository<Todo, Long> {
}
