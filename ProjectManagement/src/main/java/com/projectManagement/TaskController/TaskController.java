package com.projectManagement.TaskController;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectManagement.entity.Project;
import com.projectManagement.entity.Task;
import com.projectManagement.entity.UserClass;
import com.projectManagement.projectService.ProjectServiceInterface;
import com.projectManagement.taskService.TaskServiceInterface;
import com.projectManagement.userService.UserServiceInterface;

@Controller
public class TaskController {

	private UserServiceInterface userServiceInterface;

	private ProjectServiceInterface projectServiceInterface;

	private TaskServiceInterface taskServiceInterface;

	@Autowired
	public TaskController(UserServiceInterface userServiceInterface,
			ProjectServiceInterface projectServiceInterface, TaskServiceInterface taskServiceInterface) {
		super();
		this.userServiceInterface = userServiceInterface;
		this.projectServiceInterface = projectServiceInterface;
		this.taskServiceInterface = taskServiceInterface;
	}
	

	@RequestMapping("/ChooseProjectToCreateTask")
	public String selectProjectForTask(Model model, Authentication authentication ,Project project) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		

		UserClass userClassdetail = userServiceInterface.getUserByUsername(username);

		List<Project> projectDetail =
		projectServiceInterface.getProjectListByLeaderId(userClassdetail.getId());
		model.addAttribute("listOfProject", projectDetail);
		 
		if(projectDetail.size()>0)
			return "/leader/ChooseProjectToCreateTask";
		else
			return "/leader/NoProjectAssigned";
	}
	
	@RequestMapping("/createTask")
	public String createTaskLink(Model model, Authentication authentication ,Project project) {
		return selectProjectForTask(model, authentication, project);
	}
	
	@RequestMapping(value = "/createTask", params = "submit=Continue")
	public String createTask(Model model, Authentication authentication, Project project,
			@RequestParam("selectedProject") int projectId) {
		
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		model.addAttribute("projetId", projectId);
		
		project = projectServiceInterface.findProjectById(projectId);
		List<UserClass> listOfUser = project.getUserClasses();
		
		model.addAttribute("listOfWorkingEmployee", listOfUser);

		return "/leader/CreateTask";

	}

	@RequestMapping(value = "/generateTask",method = RequestMethod.POST)
	public String generateATask(@RequestParam("TaskTitle") String title, @RequestParam("bodyOfTask") String description,
			@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate,
			@RequestParam("selectedEmployee") int id, @RequestParam("projectId") int projectId,
			Authentication authentication, Task task, Model model) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			UserClass userClass = userServiceInterface.getUserById(id);
			Project project = projectServiceInterface.findProjectById(projectId);
			System.out.println(project.getProject_id());
			task.setTask_title(title);
			task.setAtask_desc(description);
			task.setStart_Date(startDate);
			task.setEnd_Date(endDate);
			task.setTask_project_id(project);
			task.setTask_user_id(userClass);

			taskServiceInterface.addNewTask(task);

			return allTaskDetail(model, authentication);
		
		} catch (Exception e) {
			// TODO: handle exception
			return "blank";
		}
	}

	@RequestMapping("/taskList")
	public String allTaskDetail(Model model, Authentication authentication) {
		return taskDetailListForLeader(model, authentication, 1);
	}
	
	@RequestMapping("/taskList/page/{pageNumber}")
	public String  taskDetailListForLeader(Model model, Authentication authentication,@PathVariable("pageNumber") int currentPage) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);	
		model.addAttribute("userDetail", username);
		
		UserClass userDetail = userServiceInterface.getUserByUsername(username);
		List<Project> project = projectServiceInterface.getProjectListByLeaderId(userDetail.getId());
		
		if(project.size()>0) {
			Page<Task> page= taskServiceInterface.listAllTasksForLeader(project, currentPage);
			
			long totalItems = page.getTotalElements();
			int totalPages=page.getTotalPages();
			
			List<Task> tasks = page.getContent();
			
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("totalItems",totalItems);
			model.addAttribute("totalPages",totalPages);
			model.addAttribute("taskList" , tasks);
			
			return "/leader/TaskList";
		}
		else
			return "/leader/NoTaskInTaskList";
	}

	@RequestMapping("/TaskDetail")
	public String viewOrEditTask(Model model, @RequestParam("taskId") int taskId,
			@RequestParam("projectId")int projectId ,Authentication authentication ,  Task task) {
		
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		task = taskServiceInterface.taskDetail(taskId);
		model.addAttribute("task", task);
		model.addAttribute("projectId",projectId);
		return "/leader/EditInTask";
	}

	@RequestMapping(value = "/editTask", params = "submit=cancel", method = RequestMethod.POST)
	public String cancelEditing(Model model, Authentication authentication) {
		return allTaskDetail(model, authentication);
	}

	@RequestMapping(value = "/editTask", params = "submit=Save&Continue", method = RequestMethod.POST)
	public String editInATask(Model model, @RequestParam("taskTitle") String title,
			@RequestParam("bodyOfTask") String desciption, @RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate, @RequestParam("taskId") int taskId,
			@RequestParam("projectId")int projectId , Authentication authentication ,Project project) {
		
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);

		Task projectTask = taskServiceInterface.taskDetail(taskId);
		
		projectTask.setTask_title(title);
		projectTask.setAtask_desc(desciption);
		projectTask.setStart_Date(startDate);
		projectTask.setEnd_Date(endDate);
		projectTask.setTask_Status(0);
		
		taskServiceInterface.updateTask(projectTask);
		
		model.addAttribute("projectId" , projectId);
		model.addAttribute("taskId",taskId);
		return editMember(model , taskId, projectId ,authentication, project );
	}
	
	@RequestMapping("/editEmployee")
	public String editMember(Model model , @RequestParam("taskId")int taskID ,
			@RequestParam("projectId")int projectId, Authentication authentication ,Project project ) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		project = projectServiceInterface.findProjectById(projectId);
		List<UserClass> listOfUser = project.getUserClasses();

		model.addAttribute("listOfWorkingEmployee", listOfUser);
		model.addAttribute("taskId",taskID);

		return "/leader/EditTaskEmployee";
	}

	
	@RequestMapping(value = "/chnageEmployee" , params = "Save=Save & Exit")
	public String changeMember(Model model , @RequestParam("selectedEmployee")int employeeId ,
			Task projectTask, @RequestParam("taskId")int taskId , Authentication authentication) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		UserClass userClass = userServiceInterface.getUserById(employeeId);
		
		projectTask = taskServiceInterface.taskDetail(taskId);
		
		projectTask.setTask_user_id(userClass);
		
		taskServiceInterface.updateTask(projectTask);
		
		return allTaskDetail(model, authentication);	
	}
	
	@RequestMapping(value ="/chnageEmployee", params ="cancel=Cancel")
	public String cancelAddingEmployee(Model model, Authentication authentication) {
		return allTaskDetail(model, authentication);
	}
	
	
	@RequestMapping("/tasklistOfEmployee")
	public String getTaskDetailOFEmployee(Model model , Authentication authentication , UserClass userClass) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		userClass = userServiceInterface.getUserByUsername(username);
		List<Task> completedTaskList = taskServiceInterface.completedTaskList(userClass.getId());
		model.addAttribute("completedTaskList",completedTaskList);
		 
		List<Task> pendingTaskList = taskServiceInterface.pendingTaskList(userClass.getId());
		model.addAttribute("pendingTaskList",pendingTaskList);
		 
		return"/user/TaskListOfEmployee";

	}
	
	@RequestMapping("/changeTaskStauts")
	public String changeTaskStatus(@RequestParam("taskId")int taskId , Task task ,
									Authentication authentication , Model model) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		task = taskServiceInterface.taskDetail(taskId);
		
		model.addAttribute("task",task);
		return "/user/ChnageStautsOfTask";
	}
	
	
	@RequestMapping(value = "/editTaskStatus" , params = "submit=Save")
	public String gettingnewTaskList(@RequestParam("taskId")int taskId  , 
			@RequestParam("selectTaskStatus")String status , Task task,
			Model model , Authentication authentication , UserClass userClass) {
		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		task = taskServiceInterface.taskDetail(taskId);
		Date taskCompletedDate=task.getEnd_Date(); 
		
		long millis=System.currentTimeMillis();  
        java.sql.Date currentDate=new java.sql.Date(millis);  
        
		if(status.equals("0")) {
			task.setTask_Status(0);
			System.out.println("0 is here");
		}
		else {
			if(currentDate.compareTo(taskCompletedDate)<=0)
				task.setTask_Status(1);
			else if(currentDate.compareTo(taskCompletedDate)>0)
				task.setTask_Status(2);
		}
		
		taskServiceInterface.addNewTask(task);

		return getTaskDetailOFEmployee(model, authentication, userClass);
	}
	
}
