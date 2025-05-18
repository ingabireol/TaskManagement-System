package olim.com.taskmanagementsystem.dao;

import olim.com.taskmanagementsystem.model.Category;
import java.util.List;

/**
 * Data Access Object interface for Category entity
 */
public interface CategoryDAO {
    /**
     * Create a new category
     * 
     * @param category Category to create
     * @return created Category with generated ID
     */
    Category create(Category category);
    
    /**
     * Get a category by ID
     * 
     * @param categoryId ID of the category to retrieve
     * @return Category if found, null otherwise
     */
    Category getById(int categoryId);
    
    /**
     * Get a category by name
     * 
     * @param name Name of the category to retrieve
     * @return Category if found, null otherwise
     */
    Category getByName(String name);
    
    /**
     * Get all categories
     * 
     * @return List of all categories
     */
    List<Category> getAll();
    
    /**
     * Update a category
     * 
     * @param category Category to update
     * @return true if updated successfully, false otherwise
     */
    boolean update(Category category);
    
    /**
     * Delete a category
     * 
     * @param categoryId ID of the category to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean delete(int categoryId);
    
    /**
     * Get categories for a specific task
     * 
     * @param taskId ID of the task
     * @return List of categories associated with the task
     */
    List<Category> getCategoriesByTaskId(int taskId);
    
    /**
     * Add a category to a task
     * 
     * @param taskId ID of the task
     * @param categoryId ID of the category
     * @return true if added successfully, false otherwise
     */
    boolean addCategoryToTask(int taskId, int categoryId);
    
    /**
     * Remove a category from a task
     * 
     * @param taskId ID of the task
     * @param categoryId ID of the category
     * @return true if removed successfully, false otherwise
     */
    boolean removeCategoryFromTask(int taskId, int categoryId);
}