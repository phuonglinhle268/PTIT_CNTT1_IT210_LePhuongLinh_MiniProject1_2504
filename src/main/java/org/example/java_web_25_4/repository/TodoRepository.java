package org.example.java_web_25_4.repository;

import org.example.java_web_25_4.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
