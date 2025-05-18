package olim.com.taskmanagementsystem.service;

import olim.com.taskmanagementsystem.model.Category;
import java.util.List;

/**
 * Service interface for Category-related operations
 */
public interface CategoryService {
    /**
     * Create a new category
     * 
     * @param category Category to create
     * @return created Category with generated ID
     */
    Category createCategory(Category category);
    
    /**
     * Get a category by ID
     * 
     * @param categoryId ID of the category to retrieve
     * @return Category if found, null otherwise
     */
    Category getCategoryById(int categoryId);
    
    /**
     * Get a category by name
     * 
     * @param name Name of the category to retrieve
     * @return Category if found, null otherwise
     */
    Category getCategoryByName(String name);
    
    /**
     * Get all categories
     * 
     * @return List of all categories
     */
    List<Category> getAllCategories();
    
    /**
     * Update a category
     * 
     * @param category Category to update
     * @return true if updated successfully, false otherwise
     */
    boolean updateCategory(Category category);
    
    /**
     * Delete a category
     * 
     * @param categoryId ID of the category to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteCategory(int categoryId);
    
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