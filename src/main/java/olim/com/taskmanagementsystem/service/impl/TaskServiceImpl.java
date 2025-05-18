package olim.com.taskmanagementsystem.service.impl;

import olim.com.taskmanagementsystem.dao.TaskDAO;
import olim.com.taskmanagementsystem.dao.UserDAO;
import olim.com.taskmanagementsystem.dao.impl.TaskDAOImpl;
import olim.com.taskmanagementsystem.dao.impl.UserDAOImpl;
import olim.com.taskmanagementsystem.model.Task;
import olim.com.taskmanagementsystem.model.User;
import olim.com.taskmanagementsystem.service.TaskService;

import java.util.Date;
import java.util.List;

/**
 * Implementation of TaskService interface
 */
public class TaskServiceImpl implements TaskService {
    
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    
    public TaskServiceImpl() {
        this.taskDAO = new TaskDAOImpl();
        this.userDAO = new UserDAOImpl();
    }
    
    @Override
    public Task createTask(Task task) {
        // Validate task data
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        
        // Check if creator exists
        User creator = userDAO.getById(task.getCreatedBy());
        if (creator == null) {
            throw new IllegalArgumentException("Creator user does not exist");
        }
        
        // Check if assignee exists
        User assignee = userDAO.getById(task.getAssignedTo());
        if (assignee == null) {
            throw new IllegalArgumentException("Assignee user does not exist");
        }
        
        // Set default values if not provided
        if (task.getStatus() == null) {
            task.setStatus(Task.Status.PENDING);
        }
        
        if (task.getPriority() == null) {
            task.setPriority(Task.Priority.MEDIUM);
        }
        
        // Set timestamps
        Date now = new Date();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        
        return taskDAO.create(task);
    }
    
    @Override
    public Task getTaskById(int taskId) {
        return taskDAO.getById(taskId);
    }
    
    @Override
    public List<Task> getAllTasks() {
        return taskDAO.getAll();
    }
    
    @Override
    public List<Task> getTasksByAssignedUser(int userId) {
        // Check if user exists
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        
        return taskDAO.getByAssignedUser(userId);
    }
    
    @Override
    public List<Task> getTasksByCreatedUser(int userId) {
        // Check if user exists
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        
        return taskDAO.getByCreatedUser(userId);
    }
    
    @Override
    public List<Task> getTasksByStatus(Task.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        
        return taskDAO.getByStatus(status);
    }
    
    @Override
    public List<Task> getTasksByPriority(Task.Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        
        return taskDAO.getByPriority(priority);
    }
    
    @Override
    public List<Task> getTasksByDueDateBefore(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        return taskDAO.getByDueDateBefore(date);
    }
    
    @Override
    public boolean updateTask(Task task) {
        // Validate task data
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        
        // Check if task exists
        Task existingTask = taskDAO.getById(task.getTaskId());
        if (existingTask == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        // Check if assignee exists
        User assignee = userDAO.getById(task.getAssignedTo());
        if (assignee == null) {
            throw new IllegalArgumentException("Assignee user does not exist");
        }
        
        // Preserve creation information
        task.setCreatedBy(existingTask.getCreatedBy());
        task.setCreatedAt(existingTask.getCreatedAt());
        
        // Update the timestamp
        task.setUpdatedAt(new Date());
        
        return taskDAO.update(task);
    }
    
    @Override
    public boolean deleteTask(int taskId) {
        // Check if task exists
        Task existingTask = taskDAO.getById(taskId);
        if (existingTask == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        return taskDAO.delete(taskId);
    }
    
    @Override
    public List<Task> getTasksByCategory(int categoryId) {
        return taskDAO.getByCategory(categoryId);
    }
    
    @Override
    public boolean assignTaskToUser(int taskId, int userId) {
        // Check if task exists
        Task task = taskDAO.getById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        // Check if user exists
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        
        task.setAssignedTo(userId);
        task.setUpdatedAt(new Date());
        
        return taskDAO.update(task);
    }
    
    @Override
    public boolean updateTaskStatus(int taskId, Task.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        
        // Check if task exists
        Task task = taskDAO.getById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        task.setStatus(status);
        task.setUpdatedAt(new Date());
        
        return taskDAO.update(task);
    }
}