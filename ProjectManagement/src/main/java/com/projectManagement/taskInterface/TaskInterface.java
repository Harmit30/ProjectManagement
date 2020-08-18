package com.projectManagement.taskInterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectManagement.entity.Task;

public interface TaskInterface extends JpaRepository<Task, Integer> {

}
