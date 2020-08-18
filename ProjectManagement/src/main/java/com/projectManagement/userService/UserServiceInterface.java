package com.projectManagement.userService;

import java.util.List;

import org.springframework.data.domain.Page;

import com.projectManagement.entity.UserClass;

public interface UserServiceInterface {

	public List<UserClass> getAllUser();
	
	public UserClass addNewUser(UserClass userClass);
	
	public UserClass getUserById(int id); 
	
	public List<UserClass> getAllEmployee();
	
	public int updateEmployee(int id , String fName , String lname , String userName ,String role, int blockorNot);
	
	public int updateEmployeeWithoutRole(int id, String fName, String lname, String userName, int blockorNot);
		
	public List<UserClass> getUserId(int id);
	
	public List<UserClass> getAllLeader();
	
	public List<UserClass> getAllUsersWithUserRole();

	public List<UserClass> getAllUserWthoutProject();

	public Page<UserClass> listAll(int pageNumber);

	public void removeUserFromProject(UserClass userClass);

	public UserClass getUserByUsername(String username);



	
}
