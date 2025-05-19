package olim.com.taskmanagementsystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import olim.com.taskmanagementsystem.model.Category;
import olim.com.taskmanagementsystem.service.CategoryService;
import olim.com.taskmanagementsystem.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling category-related requests
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/categories/*"})
public class CategoryServlet extends BaseServlet {
    
    private final CategoryService categoryService;
    
    public CategoryServlet() {
        this.categoryService = new CategoryServiceImpl();
    }
    
    /**
     * Handle GET requests - list categories, show category details, show create/edit forms
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        if (!handleAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all categories
            handleListCategories(request, response);
            return;
        }
        
        // Extract ID from path if present
        int categoryId = -1;
        try {
            if (pathInfo.length() > 1) {
                String[] segments = pathInfo.split("/");
                if (segments.length > 1) {
                    categoryId = Integer.parseInt(segments[1]);
                }
            }
        } catch (NumberFormatException e) {
            // Invalid category ID, continue with normal processing
        }
        
        if (pathInfo.equals("/create")) {
            // Show create form
            forwardToJsp(request, response, "/categories/categoryForm.jsp");
        } else if (pathInfo.matches("/\\d+/edit")) {
            // Show edit form
            handleShowEditForm(request, response, categoryId);
        } else if (pathInfo.matches("/\\d+")) {
            // Show category details
            handleShowCategoryDetails(request, response, categoryId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Handle POST requests - create or update categories
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        if (!handleAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/create")) {
            // Create a new category
            handleCreateCategory(request, response);
        } else if (pathInfo.matches("/\\d+")) {
            // Update an existing category
            int categoryId = Integer.parseInt(pathInfo.substring(1));
            handleUpdateCategory(request, response, categoryId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Handle DELETE requests - delete categories
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        if (!handleAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.matches("/\\d+")) {
            // Delete a category
            int categoryId = Integer.parseInt(pathInfo.substring(1));
            handleDeleteCategory(request, response, categoryId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * List all categories
     */
    private void handleListCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAllCategories();
        
        // Set attributes
        request.setAttribute("categories", categories);
        
        // Forward to category list page
        forwardToJsp(request, response, "/categories/categoryList.jsp");
    }
    
    /**
     * Show category details
     */
    private void handleShowCategoryDetails(HttpServletRequest request, HttpServletResponse response, int categoryId) throws ServletException, IOException {
        Category category = categoryService.getCategoryById(categoryId);
        
        if (category == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Set attributes
        request.setAttribute("category", category);
        
        // Forward to category details page
        forwardToJsp(request, response, "/categories/categoryDetails.jsp");
    }
    
    /**
     * Show category edit form
     */
    private void handleShowEditForm(HttpServletRequest request, HttpServletResponse response, int categoryId) throws ServletException, IOException {
        Category category = categoryService.getCategoryById(categoryId);
        
        if (category == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Set attributes
        request.setAttribute("category", category);
        
        // Forward to category form page
        forwardToJsp(request, response, "/categories/categoryForm.jsp");
    }
    
    /**
     * Create a new category
     */
    private void handleCreateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Parse request parameters
            Category category = parseCategoryFromRequest(request);
            
            // Create the category
            category = categoryService.createCategory(category);
            
            if (category != null) {
                // Redirect to category list
                response.sendRedirect(request.getContextPath() + "/categories");
            } else {
                throw new ServletException("Failed to create category");
            }
        } catch (Exception e) {
            // Re-populate form and show error
            request.setAttribute("error", e.getMessage());
            forwardToJsp(request, response, "/categories/categoryForm.jsp");
        }
    }
    
    /**
     * Update an existing category
     */
    private void handleUpdateCategory(HttpServletRequest request, HttpServletResponse response, int categoryId) throws ServletException, IOException {
        Category existingCategory = categoryService.getCategoryById(categoryId);
        
        if (existingCategory == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            // Parse request parameters
            Category updatedCategory = parseCategoryFromRequest(request);
            updatedCategory.setCategoryId(categoryId);
            
            // Update the category
            boolean success = categoryService.updateCategory(updatedCategory);
            
            if (success) {
                // Redirect to category list
                response.sendRedirect(request.getContextPath() + "/categories");
            } else {
                throw new ServletException("Failed to update category");
            }
        } catch (Exception e) {
            // Re-populate form and show error
            request.setAttribute("category", existingCategory);
            request.setAttribute("error", e.getMessage());
            forwardToJsp(request, response, "categories/categoryForm.jsp");
        }
    }
    
    /**
     * Delete a category
     */
    private void handleDeleteCategory(HttpServletRequest request, HttpServletResponse response, int categoryId) throws IOException {
        try {
            boolean success = categoryService.deleteCategory(categoryId);
            
            if (success) {
                sendSuccessResponse(response, "Category deleted successfully");
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete category");
            }
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    /**
     * Parse category data from request parameters
     */
    private Category parseCategoryFromRequest(HttpServletRequest request) throws IllegalArgumentException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }
        
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        
        return category;
    }
}