package com.projectManagement.projectController;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectManagement.entity.Project;
import com.projectManagement.entity.UserClass;
import com.projectManagement.projectService.ProjectServiceInterface;
import com.projectManagement.userService.UserServiceInterface;

@Controller
public class ProjectController {

	private ProjectServiceInterface projectServiceInterface;

	private UserServiceInterface userServiceInterFace;

	@Autowired
	public ProjectController(ProjectServiceInterface projectServiceInterface,
			UserServiceInterface userServiceInterFace) {
		super();
		this.projectServiceInterface = projectServiceInterface;
		this.userServiceInterFace = userServiceInterFace;
	}

	@RequestMapping("/addNewProject")
	public String addNewProject(Model model, Project project, Authentication authentication) {
	
	try {
		
	String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		List<UserClass> listOfEmployee = userServiceInterFace.getAllLeader();
		model.addAttribute("listOfAvailableEmployee", listOfEmployee);
		return "/admin/CreateProject";
		
	} catch (Exception exiption) { 
		return "blank";
	}
	}

	@RequestMapping("/createProject")
	public String generateNewProject(@RequestParam("projectTitle") String title,
			@RequestParam("bodyOfProject") String desc, @RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate, @RequestParam("selectedEmployee") String selectedLeader,
			Authentication authentication, Model model, Project project) {
		try {
		
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			int leaderID = Integer.parseInt(selectedLeader);
			UserClass leaderDetail = userServiceInterFace.getUserById(leaderID);

			project.setTitle(title);
			project.setDescription(desc);
			project.setStart_date(startDate);
			project.setEnd_date(endDate);
			project.setLeaderId(leaderDetail);

			projectServiceInterface.addNewProject(project);

			List<UserClass> employeeDetail = userServiceInterFace.getAllUsersWithUserRole();
			model.addAttribute("emoloyeeList", employeeDetail);
			
			Project project2 = projectServiceInterface.findProject(title);
			model.addAttribute("projectId", project2.getProject_id());
			
			project = projectServiceInterface.findProjectById(project2.getProject_id());

			List<UserClass> listOfUser = project.getUserClasses();

			model.addAttribute("listOfWorkingEmployee", listOfUser);

			List<UserClass> getAllUser = userServiceInterFace.getAllUsersWithUserRole();

			getAllUser.removeAll(listOfUser);

			model.addAttribute("listOfRemainEmployee", getAllUser);

			model.addAttribute("projectId", project2.getProject_id());
			
			
			return "/admin/ProjectUpdateEmployees";

		} catch (Exception e) {

			return "blank";
		}
			}
	
	
	
	
	
}
