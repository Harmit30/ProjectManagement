package com.projectManagement.SpringAOP;

import java.util.logging.Logger;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringAOP {
static	Logger logger=Logger.getLogger(SpringAOP.class.getName());
	
	//userServiceImpl
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getAllUser(..))")
	public void UserServiceGetAllUser() {
		logger.info("Class = UserService method = getAllUser running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.addNewUser(..))")
	public void UserServiceAddNewUser() {
		logger.info("Class = UserService method = addNewUser running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.removeUserFromProject(..))")
	public void UserServiceRemoveUserFromProject() {
		logger.info("Class = UserService method = removeUserFromProject running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getUserById(..))")
	public void UserServiceGetUserById() {
		logger.info("Class = UserService method = getUserById running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.updateEmployee(..))")
	public void UserServiceUpdateEmployee() {
		logger.info("Class = UserService method = updateEmployee running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.updateEmployeeWithoutRole(..))")
	public void UserServiceUpdateEmployeeWithoutRole() {
		logger.info("Class = UserService method = updateEmployeeWithoutRole running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getUserId(..))")
	public void UserServiceGetUserId() {
		logger.info("Class = UserService method = getUserId running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getAllLeader(..))")
	public void UserServiceGetAllLeader() {
		logger.info("Class = UserService method = getAllLeader  running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getAllUsersWithUserRole(..))")
	public void UserServiceGetAllUsersWithUserRole() {
		logger.info("Class = UserService method = getAllUsersWithUserRole running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getAllUserWthoutProject(..))")
	public void UserServiceGetAllUserWthoutProject() {
		logger.info("Class = UserService method = getAllUserWthoutProject running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getAllEmployee(..))")
	public void UserServiceGetAllEmployee() {
		logger.info("Class = UserService method = getAllEmployee running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.listAll(..))")
	public void UserServiceListAll() {
		logger.info("Class = UserService method = listAll running");
	}
	
	@After("execution(* com.projectManagement.userService.UserServiceImpl.getUserByUsername(..))")
	public void UserServiceGetUserByUsername() {
		logger.info("Class = UserService method = getUserByUsername running");
	}
	
	
	//projectServiceImpl
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.addNewProject(..))")
	public void ProjectServiceAddNewProject() {
		logger.info("Class = ProjectService method = addNewProject running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.removeProjectFromUser(..))")
	public void ProjectServiceRemoveProjectFromUser() {
		logger.info("Class = ProjectService method = removeProjectFromUser running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.findProject(..))")
	public void ProjectServiceFindProject() {
		logger.info("Class = ProjectService method = findProject running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.findProjectById(..))")
	public void ProjectServiceFindProjectById() {
		logger.info("Class = ProjectService method = findProjectById running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.listOfAllProject(..))")
	public void ProjectServiceListOfAllProject() {
		logger.info("Class = ProjectService method = listOfAllProject running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.lastFiveProjects(..))")
	public void ProjectServiceLastFiveProjects() {
		logger.info("Class = ProjectService method = lastFiveProjects running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.getRunningProjects(..))")
	public void ProjectServiceGetRunningProjects() {
		logger.info("Class = ProjectService method = getRunningProjects running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.findAllProject(..))")
	public void ProjectServiceFindAllProject() {
		logger.info("Class = ProjectService method = findAllProject running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.updateDetail(..))")
	public void ProjectServiceUpdateDetail() {
		logger.info("Class = ProjectService method = updateDetail running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.updateLeader(..))")
	public void ProjectServiceUpdateLeader() {
		logger.info("Class = ProjectService method = updateLeader running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.getProjectListByLeaderId(..))")
	public void ProjectServiceGetProjectListByLeaderId() {
		logger.info("Class = ProjectService method = getProjectListByLeaderId running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.lastFiveProjectListByLeaderId(..))")
	public void ProjectServiceLastFiveProjectListByLeaderId() {
		logger.info("Class = ProjectService method = lastFiveProjectListByLeaderId running");
	}
	
	@After("execution(* com.projectManagement.projectService.ProjectServiceimpl.getProjectListWithEndDate(..))")
	public void ProjectServiceGetProjectListWithEndDate() {
		logger.info("Class = ProjectService method = getProjectListWithEndDate running");
	}
	
	
	//taskServiceImpl
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.addNewTask(..))")
	public void TaskServiceAddNewTask() {
		logger.info("Class = taskService method = addNewTask running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.getAllTask(..))")
	public void TaskServiceGetAllTask() {
		logger.info("Class = taskService method = getAllTask running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.taskDetail(..))")
	public void TaskServiceTaskDetail() {
		logger.info("Class = taskService method = taskDetail running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.updateInATask(..))")
	public void TaskServiceUpdateInATask() {
		logger.info("Class = taskService method = updateInATask running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.taskListByProjectId(..))")
	public void TaskServiceTaskListByProjectId() {
		logger.info("Class = taskService method = taskListByProjectId running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.listOfPendingTaskbyProjectIdForLeader(..))")
	public void TaskServiceListOfPendingTaskbyProjectIdForLeader() {
		logger.info("Class = taskService method = listOfPendingTaskbyProjectIdForLeader running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.listOfCompletedTaskbyProjectIdForLeader(..))")
	public void TaskServiceListOfCompletedTaskbyProjectIdForLeader() {
		logger.info("Class = taskService method = listOfCompletedTaskbyProjectIdForLeader running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.updateTask(..))")
	public void TaskServiceUpdateTask() {
		logger.info("Class = taskService method = updateTask running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.userTaskDetail(..))")
	public void TaskServiceUserTaskDetail() {
		logger.info("Class = taskService method = userTaskDetail running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.pendingTaskList(..))")
	public void TaskServicePendingTaskList() {
		logger.info("Class = taskService method = pendingTaskList running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.completedTaskList(..))")
	public void TaskServiceCompletedTaskList() {
		logger.info("Class = taskService method = completedTaskList running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.totalTasksForEmployee(..))")
	public void TaskServiceTotalTasksForEmployee() {
		logger.info("Class = taskService method = totalTasksForEmployee running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.lastFivePendingTasks(..))")
	public void TaskServiceLastFivePendingTasks() {
		logger.info("Class = taskService method = lastFivePendingTasks running");
	}
	
	@After("execution(* com.projectManagement.taskService.TaskServiceImpl.listAllTasksForLeader(..))")
	public void TaskServiceListAllTasksForLeader() {
		logger.info("Class = taskService method = listAllTasksForLeader running");
	}

}
