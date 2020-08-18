package com.projectManagement.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectManagement.entity.Project;
import com.projectManagement.entity.Task;
import com.projectManagement.entity.UserClass;
import com.projectManagement.projectService.ProjectServiceInterface;
import com.projectManagement.taskService.TaskServiceInterface;
import com.projectManagement.userService.UserServiceInterface;

@Controller
public class HomeController {

	private ProjectServiceInterface projectServiceInterface;

	private UserServiceInterface userServiceInterface;

	private TaskServiceInterface taskServiceInterface;

	@Autowired
	public HomeController(ProjectServiceInterface projectServiceInterface, UserServiceInterface userServiceInterface,
			TaskServiceInterface taskServiceInterface) {
		super();
		this.projectServiceInterface = projectServiceInterface;
		this.userServiceInterface = userServiceInterface;
		this.taskServiceInterface = taskServiceInterface;
	}

	@RequestMapping("/")
	private String goToHomePage(Model model , Authentication authentication) {
		
		String username = authentication.getName();
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		String roles = "[ROLE_ADMIN]";
		String userRole = "[ROLE_LEADER]";
		UserClass userClass = userServiceInterface.getUserByUsername(username);
		
		model.addAttribute("userDetail", username);	

		//---------ADMIN--------------
		if (checkAuthority.equals(roles)) {
			//total number of projects - admin home page
			int totalProject=projectServiceInterface.findAllProject().size();
			model.addAttribute("totalProject",totalProject);
			
			//total number of employees - admin home page
			int totalEmployees=userServiceInterface.getAllUser().size();
			model.addAttribute("totalEmployees",totalEmployees);
			
			//list of five projects - admin home page
			List<Project> listOfFiveProjects=projectServiceInterface.lastFiveProjects();
			model.addAttribute("listOfFiveProjects",listOfFiveProjects);
			
			//list of running projects - admin home page
			int listOfRunningProjects=projectServiceInterface.getRunningProjects().size();
			model.addAttribute("listOfRunningProjects",listOfRunningProjects);	
			
			model.addAttribute("userRole", checkAuthority);
			return "/admin/HomePage";
		}
		//-------------LEADER----------------
		else  if (checkAuthority.equals(userRole)) {
			//last Five Project As Leader - leader home page
			List<Project> lastFiveProjectForLeader=projectServiceInterface.lastFiveProjectListByLeaderId(userClass.getId());
			model.addAttribute("lastFiveProjectForLeader",lastFiveProjectForLeader);
			
			//number of all tasks - leader home page
			List<Project> project = projectServiceInterface.getProjectListByLeaderId(userClass.getId());		
			Set<Task> listOfTotalTask=new HashSet<>();
			Set<Task> listOfPendingTask=new HashSet<>();
			Set<Task> listOfCompletedTask=new HashSet<>();

			List<Task> listOfTotalTaskTemp=null;
			List<Task> listOfPendingTaskTemp = null;
			List<Task> listOfCompletedTaskTemp = null;
			
			for (Project project2 : project) {
				int projectId = project2.getProject_id();
				
				//number of all tasks - leader home page
				listOfTotalTaskTemp = taskServiceInterface.taskListByProjectId(projectId);
				listOfTotalTask.addAll(listOfTotalTaskTemp);
				
				//number of pending task - leader home page
				listOfPendingTaskTemp = taskServiceInterface.listOfPendingTaskbyProjectIdForLeader(projectId);
				listOfPendingTask.addAll(listOfPendingTaskTemp);
				
				//number of completed task - leader home page
				listOfCompletedTaskTemp = taskServiceInterface.listOfCompletedTaskbyProjectIdForLeader(projectId);
				listOfCompletedTask.addAll(listOfCompletedTaskTemp);
			}
			int numberOfTotalTask=listOfTotalTask.size();
			model.addAttribute("numberOfTotalTask",numberOfTotalTask);
			
			int numberOfPendingTask=listOfPendingTask.size();
			model.addAttribute("numberOfPendingTask",numberOfPendingTask);

			int numberOfCompletedTask=listOfCompletedTask.size();
			model.addAttribute("numberOfCompletedTask",numberOfCompletedTask);
			
			model.addAttribute("userRole", checkAuthority);
			return"/leader/HomePageForLeader";
		}
		//----------USER---------------
		else {
			model.addAttribute("userRole", checkAuthority);
			//number of completed task - employee home page
			int numberOfCompletedTaskOfEmployee = taskServiceInterface.completedTaskList(userClass.getId()).size();
			model.addAttribute("numberOfCompletedTaskOfEmployee",numberOfCompletedTaskOfEmployee);
					
			//number of pending task - employee home page
			int numberOfPendingTaskOfEmployee = taskServiceInterface.pendingTaskList(userClass.getId()).size();
			model.addAttribute("numberOfPendingTaskOfEmployee",numberOfPendingTaskOfEmployee);
			
			//number of total task - employee home page
			int numberOfTotalTasksForEmployee=taskServiceInterface.totalTasksForEmployee(userClass.getId()).size();
			model.addAttribute("numberOfTotalTasksForEmployee", numberOfTotalTasksForEmployee);
			
			//number of last five pending task - employee home page
			List<Task> lastFivePendingTasksForEmployee=taskServiceInterface.lastFivePendingTasks(userClass.getId());
			model.addAttribute("lastFivePendingTasksForEmployee", lastFivePendingTasksForEmployee);
			
			return "/user/HomePageForUser";
		}
	}

	@RequestMapping("/againOnMainPage")
	public String goToMainPageAgain(Model model, Authentication authentication) {
		return goToHomePage(model, authentication);
	}


	
}
