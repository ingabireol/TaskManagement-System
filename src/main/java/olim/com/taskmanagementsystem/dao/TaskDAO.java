package olim.com.taskmanagementsystem.dao;

import olim.com.taskmanagementsystem.model.Task;
import java.util.List;
import java.util.Date;

/**
 * Data Access Object interface for Task entity
 */
public interface TaskDAO {
    /**
     * Create a new task
     * 
     * @param task Task to create
     * @return created Task with generated ID
     */
    Task create(Task task);
    
    /**
     * Get a task by ID
     * 
     * @param taskId ID of the task to retrieve
     * @return Task if found, null otherwise
     */
    Task getById(int taskId);
    
    /**
     * Get all tasks
     * 
     * @return List of all tasks
     */
    List<Task> getAll();
    
    /**
     * Get tasks by user ID (assigned to)
     * 
     * @param userId ID of the assigned user
     * @return List of tasks assigned to the user
     */
    List<Task> getByAssignedUser(int userId);
    
    /**
     * Get tasks by created user ID
     * 
     * @param userId ID of the user who created the tasks
     * @return List of tasks created by the user
     */
    List<Task> getByCreatedUser(int userId);
    
    /**
     * Get tasks by status
     * 
     * @param status Status to filter by
     * @return List of tasks with the specified status
     */
    List<Task> getByStatus(Task.Status status);
    
    /**
     * Get tasks by priority
     * 
     * @param priority Priority to filter by
     * @return List of tasks with the specified priority
     */
    List<Task> getByPriority(Task.Priority priority);
    
    /**
     * Get tasks due before a specific date
     * 
     * @param date Date to compare against
     * @return List of tasks due before the specified date
     */
    List<Task> getByDueDateBefore(Date date);
    
    /**
     * Update a task
     * 
     * @param task Task to update
     * @return true if updated successfully, false otherwise
     */
    boolean update(Task task);
    
    /**
     * Delete a task
     * 
     * @param taskId ID of the task to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean delete(int taskId);
    
    /**
     * Get tasks by category ID
     * 
     * @param categoryId ID of the category
     * @return List of tasks with the specified category
     */
    List<Task> getByCategory(int categoryId);
}