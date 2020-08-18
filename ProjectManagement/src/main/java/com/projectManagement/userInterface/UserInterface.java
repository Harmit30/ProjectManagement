package com.projectManagement.userInterface;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projectManagement.entity.UserClass;

public interface UserInterface extends JpaRepository<UserClass, Integer> {

	
}
