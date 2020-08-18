package com.projectManagement.taskService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Converts;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projectManagement.entity.Project;
import com.projectManagement.entity.Task;
import com.projectManagement.taskInterface.TaskInterface;

@Service
public class TaskServiceImpl implements TaskServiceInterface {

	private TaskInterface taskInterface;

	private EntityManager entityManager;

	@Autowired
	public TaskServiceImpl(TaskInterface taskInterface, EntityManager entityManager) {
		super();
		this.taskInterface = taskInterface;
		this.entityManager = entityManager;
	}

	@Override
	public Task addNewTask(Task task) {
		return taskInterface.save(task);
	}

	@Override
	public List<Task> getAllTask() {
		// TODO Auto-generated method stub
		return taskInterface.findAll();
	}

	@Override
	public Task taskDetail(int id) {
		// TODO Auto-generated method stub
		return taskInterface.getOne(id);
	}

	@Override
	@Transactional
	public int updateInATask( String title, String description, Date startDate, Date endDate , int taskID) {
		Session session = entityManager.unwrap(Session.class);

		Query query = session.createQuery("update ProjectTask set task_title ='" + title + "', atask_desc ='"+description+"', start_Date= '"+startDate+"',end_Date='"+endDate+"'where task_id = '"+taskID+"'");
		int updated = query.executeUpdate();

		return updated;
	}
	
	@Override
	@Transactional
	public List<Task> taskListByProjectId(int projectId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("From Task where task_project_id='"+projectId+"'");
		List<Task> taskListOfEmployee  = query.list();

		return taskListOfEmployee;
	}
	
	@Override
	@Transactional
	public List<Task> listOfPendingTaskbyProjectIdForLeader(int projectId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("FROM Task where task_project_id="+projectId+" and task_Status=0");
		List<Task> taskListOfEmployee  = query.list();
		
		return taskListOfEmployee;
	}
	
	@Override
	@Transactional
	public List<Task> listOfCompletedTaskbyProjectIdForLeader(int projectId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("From Task where task_project_id='"+projectId+"' and (task_Status=1 or task_Status=2)");
		List<Task> taskListOfEmployee  = query.list();
		
		return taskListOfEmployee;
	}

	@Override
	public Task updateTask(Task projectTask) {

		return taskInterface.save(projectTask);
	}

	@Override
	@Transactional
	public List<Task> userTaskDetail(int userID) {
		
		Session session = entityManager.unwrap(Session.class);

		Query<Task> taskList = session.createQuery("from Task where task_user_id="+userID);
		
		List<Task> taskListOfEmployee  = taskList.list();
		
		return taskListOfEmployee;
	}

	//this is for employee
	@Override
	@Transactional
	public List<Task> pendingTaskList(int userID) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> taskStartStatus = session.createQuery("from Task where task_user_id = '"+userID+"' and task_Status = 0");
		
		List<Task> taskStatus  = taskStartStatus.list();
		
		return taskStatus;
	}

	
	//this is for employee
	@Override
	@Transactional
	public List<Task> completedTaskList(int userID) {
		
		Session session = entityManager.unwrap(Session.class);

		Query<Task> taskWithNoStatus = session.createQuery("from Task where task_user_id ='"+userID+"' and (task_Status=1 or task_Status=2)");
		
		List<Task> taskWithoutStatus = taskWithNoStatus.list();
		return taskWithoutStatus;
	}
	
	//this is for employee
	@Override
	@Transactional
	public List<Task> totalTasksForEmployee(int userID) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("from Task where task_user_id = '"+userID+"'");
		
		List<Task> totalTasks  = query.list();
		
		return totalTasks;
	}
	
	//this is for employee
	@Override
	@Transactional
	public List<Task> lastFivePendingTasks(int userID) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("from Task where task_user_id = '"+userID+"' and task_Status = 0").setMaxResults(5);
		
		List<Task> totalTasks  = query.list();
		
		return totalTasks;
	}
	
	//------------list of leader task in pagination-----------
	@Override
	@Transactional
	public Page<Task> listAllTasksForLeader(List<Project> project ,int pageNumber) {
		
		Pageable pageable=PageRequest.of(pageNumber-1, 6);
		Session session = entityManager.unwrap(Session.class);
		List<Task> totalTaskTemp=null;
		Set<Task> totalTaskHash=new HashSet<>();
		List<Task> totalTask=null;
	
		for (Project project2 : project) {
			Query<Task> query = session.createQuery("from Task where task_project_id = '"+project2.getProject_id()+"'");
			totalTaskTemp  = query.list();
			totalTaskHash.addAll(totalTaskTemp);
		}
		
		totalTask=new ArrayList<>(totalTaskHash);
		
		int start=(int) pageable.getOffset();
	    int end = (start + pageable.getPageSize()) > totalTask.size() ? totalTask.size() : (start + pageable.getPageSize());
		
	    Page<Task> totalTaskPage=new PageImpl<Task>(totalTask.subList(start, end),pageable,totalTask.size());
		
		System.out.println(totalTaskPage.getSize());
		System.out.println(totalTaskPage.getTotalElements());
		System.out.println(totalTaskPage.getTotalPages());
		
		
		for (Task task : totalTaskPage) {
			System.out.println(task.getTask_id() + "=" + task.getTask_title());
		}
		
		return totalTaskPage;
	//	return taskInterface.findAll(pageable);
		
	}


}
