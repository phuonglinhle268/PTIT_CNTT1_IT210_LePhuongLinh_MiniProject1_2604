package org.example.java_web_26_4.repository;

import org.example.java_web_26_4.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
