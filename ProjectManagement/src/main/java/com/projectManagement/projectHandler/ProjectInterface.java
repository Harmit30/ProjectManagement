package com.projectManagement.projectHandler;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectManagement.entity.Project;

public interface ProjectInterface extends JpaRepository<Project,Integer> {


	
}
