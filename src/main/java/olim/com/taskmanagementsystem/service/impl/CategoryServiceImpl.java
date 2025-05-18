package olim.com.taskmanagementsystem.service.impl;

import olim.com.taskmanagementsystem.dao.CategoryDAO;
import olim.com.taskmanagementsystem.dao.TaskDAO;
import olim.com.taskmanagementsystem.dao.impl.CategoryDAOImpl;
import olim.com.taskmanagementsystem.dao.impl.TaskDAOImpl;
import olim.com.taskmanagementsystem.model.Category;
import olim.com.taskmanagementsystem.model.Task;
import olim.com.taskmanagementsystem.service.CategoryService;

import java.util.List;

/**
 * Implementation of CategoryService interface
 */
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryDAO categoryDAO;
    private final TaskDAO taskDAO;
    
    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
        this.taskDAO = new TaskDAOImpl();
    }
    
    @Override
    public Category createCategory(Category category) {
        // Validate category data
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        
        // Check if category with the same name already exists
        Category existingCategory = categoryDAO.getByName(category.getName());
        if (existingCategory != null) {
            throw new IllegalArgumentException("Category with this name already exists");
        }
        
        return categoryDAO.create(category);
    }
    
    @Override
    public Category getCategoryById(int categoryId) {
        return categoryDAO.getById(categoryId);
    }
    
    @Override
    public Category getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        
        return categoryDAO.getByName(name);
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }
    
    @Override
    public boolean updateCategory(Category category) {
        // Validate category data
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        
        // Check if category exists
        Category existingCategory = categoryDAO.getById(category.getCategoryId());
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        
        // Check if new name conflicts with another category
        Category nameConflict = categoryDAO.getByName(category.getName());
        if (nameConflict != null && nameConflict.getCategoryId() != category.getCategoryId()) {
            throw new IllegalArgumentException("Category with this name already exists");
        }
        
        return categoryDAO.update(category);
    }
    
    @Override
    public boolean deleteCategory(int categoryId) {
        // Check if category exists
        Category existingCategory = categoryDAO.getById(categoryId);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        
        return categoryDAO.delete(categoryId);
    }
    
    @Override
    public List<Category> getCategoriesByTaskId(int taskId) {
        // Check if task exists
        Task task = taskDAO.getById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        return categoryDAO.getCategoriesByTaskId(taskId);
    }
    
    @Override
    public boolean addCategoryToTask(int taskId, int categoryId) {
        // Check if task exists
        Task task = taskDAO.getById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        // Check if category exists
        Category category = categoryDAO.getById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        
        // Check if the association already exists
        List<Category> taskCategories = categoryDAO.getCategoriesByTaskId(taskId);
        for (Category c : taskCategories) {
            if (c.getCategoryId() == categoryId) {
                return true; // Already associated
            }
        }
        
        return categoryDAO.addCategoryToTask(taskId, categoryId);
    }
    
    @Override
    public boolean removeCategoryFromTask(int taskId, int categoryId) {
        // Check if task exists
        Task task = taskDAO.getById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task does not exist");
        }
        
        // Check if category exists
        Category category = categoryDAO.getById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        
        return categoryDAO.removeCategoryFromTask(taskId, categoryId);
    }
}