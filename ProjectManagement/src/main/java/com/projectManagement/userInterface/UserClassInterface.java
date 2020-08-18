package com.projectManagement.userInterface;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.projectManagement.entity.UserClass;

public interface UserClassInterface extends PagingAndSortingRepository<UserClass, Integer> {

}
