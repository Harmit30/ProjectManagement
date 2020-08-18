package com.projectManagement.projectService;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.projectManagement.entity.Project;
import com.projectManagement.entity.UserClass;

public interface ProjectServiceInterface {

	public Project addNewProject(Project project);
	
	public Project findProject(String projectName);
	
	public Project findProjectById(int id);
	
	public void removeProjectFromUser(Project project);
	
	public List<Project> listOfAllProject(int id);
	
	public List<Project> findAllProject();
	
	public List<Project> lastFiveProjects();
	
	public int updateDetail(int id ,String title , String description , Date startdate , Date endate);

	public int updateLeader(int leaderId , int projectId);

	public List<Project> getRunningProjects();

	public List<Project> getProjectListByLeaderId(int leaderId);

	public List<Project> getProjectListWithEndDate(int id);

	public List<Project> lastFiveProjectListByLeaderId(int leaderId);

	public Page<Project> listAll(int pageNumber);
}
