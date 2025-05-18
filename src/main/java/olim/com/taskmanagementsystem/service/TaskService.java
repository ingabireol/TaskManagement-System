package olim.com.taskmanagementsystem.service;

import olim.com.taskmanagementsystem.model.Task;
import java.util.Date;
import java.util.List;

/**
 * Service interface for Task-related operations
 */
public interface TaskService {
    /**
     * Create a new task
     * 
     * @param task Task to create
     * @return created Task with generated ID
     */
    Task createTask(Task task);
    
    /**
     * Get a task by ID
     * 
     * @param taskId ID of the task to retrieve
     * @return Task if found, null otherwise
     */
    Task getTaskById(int taskId);
    
    /**
     * Get all tasks
     * 
     * @return List of all tasks
     */
    List<Task> getAllTasks();
    
    /**
     * Get tasks assigned to a specific user
     * 
     * @param userId ID of the assigned user
     * @return List of tasks assigned to the user
     */
    List<Task> getTasksByAssignedUser(int userId);
    
    /**
     * Get tasks created by a specific user
     * 
     * @param userId ID of the user who created the tasks
     * @return List of tasks created by the user
     */
    List<Task> getTasksByCreatedUser(int userId);
    
    /**
     * Get tasks by status
     * 
     * @param status Status to filter by
     * @return List of tasks with the specified status
     */
    List<Task> getTasksByStatus(Task.Status status);
    
    /**
     * Get tasks by priority
     * 
     * @param priority Priority to filter by
     * @return List of tasks with the specified priority
     */
    List<Task> getTasksByPriority(Task.Priority priority);
    
    /**
     * Get tasks due before a specific date
     * 
     * @param date Date to compare against
     * @return List of tasks due before the specified date
     */
    List<Task> getTasksByDueDateBefore(Date date);
    
    /**
     * Update a task
     * 
     * @param task Task to update
     * @return true if updated successfully, false otherwise
     */
    boolean updateTask(Task task);
    
    /**
     * Delete a task
     * 
     * @param taskId ID of the task to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteTask(int taskId);
    
    /**
     * Get tasks by category
     * 
     * @param categoryId ID of the category
     * @return List of tasks with the specified category
     */
    List<Task> getTasksByCategory(int categoryId);
    
    /**
     * Assign a task to a user
     * 
     * @param taskId ID of the task
     * @param userId ID of the user
     * @return true if assigned successfully, false otherwise
     */
    boolean assignTaskToUser(int taskId, int userId);
    
    /**
     * Update the status of a task
     * 
     * @param taskId ID of the task
     * @param status New status
     * @return true if updated successfully, false otherwise
     */
    boolean updateTaskStatus(int taskId, Task.Status status);
}