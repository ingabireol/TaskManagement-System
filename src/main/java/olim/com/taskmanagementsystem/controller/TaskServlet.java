package olim.com.taskmanagementsystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import olim.com.taskmanagementsystem.model.Category;
import olim.com.taskmanagementsystem.model.Task;
import olim.com.taskmanagementsystem.model.User;
import olim.com.taskmanagementsystem.service.CategoryService;
import olim.com.taskmanagementsystem.service.TaskService;
import olim.com.taskmanagementsystem.service.UserService;
import olim.com.taskmanagementsystem.service.impl.CategoryServiceImpl;
import olim.com.taskmanagementsystem.service.impl.TaskServiceImpl;
import olim.com.taskmanagementsystem.service.impl.UserServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet for handling task-related requests
 */
@WebServlet(name = "TaskServlet", urlPatterns = {"/tasks/*"})
public class TaskServlet extends BaseServlet {
    
    private final TaskService taskService;
    private final UserService userService;
    private final CategoryService categoryService;
    
    public TaskServlet() {
        this.taskService = new TaskServiceImpl();
        this.userService = new UserServiceImpl();
        this.categoryService = new CategoryServiceImpl();
    }
    
    /**
     * Handle GET requests - list tasks, show task details, show create/edit forms
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        if (!handleAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all tasks
            handleListTasks(request, response);
            return;
        }
        
        // Extract ID from path if present
        int taskId = -1;
        try {
            if (pathInfo.length() > 1) {
                String[] segments = pathInfo.split("/");
                if (segments.length > 1) {
                    taskId = Integer.parseInt(segments[1]);
                }
            }
        } catch (NumberFormatException e) {
            // Invalid task ID, continue with normal processing
        }
        
        if (pathInfo.equals("/create")) {
            // Show create form
            prepareTaskForm(request);
            forwardToJsp(request, response, "/WEB-INF/tasks/taskForm.jsp");
        } else if (pathInfo.matches("/\\d+/edit")) {
            // Show edit form
            handleShowEditForm(request, response, taskId);
        } else if (pathInfo.matches("/\\d+")) {
            // Show task details
            handleShowTaskDetails(request, response, taskId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Handle POST requests - create or update tasks
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        if (!handleAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/create")) {
            // Create a new task
            handleCreateTask(request, response);
        } else if (pathInfo.matches("/\\d+")) {
            // Update an existing task
            int taskId = Integer.parseInt(pathInfo.substring(1));
            handleUpdateTask(request, response, taskId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Handle DELETE requests - delete tasks
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        if (!handleAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.matches("/\\d+")) {
            // Delete a task
            int taskId = Integer.parseInt(pathInfo.substring(1));
            handleDeleteTask(request, response, taskId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * List all tasks with filtering options
     */
    private void handleListTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = getAuthenticatedUser(request);
        
        // Get filter parameters
        String statusFilter = request.getParameter("status");
        String priorityFilter = request.getParameter("priority");
        String categoryFilter = request.getParameter("category");
        String assigneeFilter = request.getParameter("assignee");
        
        List<Task> tasks = new ArrayList<>();
        
        // Apply filters
        if (statusFilter != null && !statusFilter.isEmpty()) {
            try {
                Task.Status status = Task.Status.valueOf(statusFilter);
                tasks = taskService.getTasksByStatus(status);
            } catch (IllegalArgumentException e) {
                tasks = taskService.getAllTasks();
                request.setAttribute("error", "Invalid status filter");
            }
        } else if (priorityFilter != null && !priorityFilter.isEmpty()) {
            try {
                Task.Priority priority = Task.Priority.valueOf(priorityFilter);
                tasks = taskService.getTasksByPriority(priority);
            } catch (IllegalArgumentException e) {
                tasks = taskService.getAllTasks();
                request.setAttribute("error", "Invalid priority filter");
            }
        } else if (categoryFilter != null && !categoryFilter.isEmpty()) {
            try {
                int categoryId = Integer.parseInt(categoryFilter);
                tasks = taskService.getTasksByCategory(categoryId);
            } catch (NumberFormatException e) {
                tasks = taskService.getAllTasks();
                request.setAttribute("error", "Invalid category filter");
            }
        } else if (assigneeFilter != null && !assigneeFilter.isEmpty()) {
            try {
                int assigneeId = Integer.parseInt(assigneeFilter);
                tasks = taskService.getTasksByAssignedUser(assigneeId);
            } catch (NumberFormatException e) {
                tasks = taskService.getAllTasks();
                request.setAttribute("error", "Invalid assignee filter");
            }
        } else {
            // No filters, get all tasks
            tasks = taskService.getAllTasks();
        }
        
        // Get all users for the assignee dropdown
        List<User> users = userService.getAllUsers();
        
        // Get all categories for the category dropdown
        List<Category> categories = categoryService.getAllCategories();
        
        // Set attributes
        request.setAttribute("tasks", tasks);
        request.setAttribute("users", users);
        request.setAttribute("categories", categories);
        request.setAttribute("statuses", Task.Status.values());
        request.setAttribute("priorities", Task.Priority.values());
        
        // Set filter parameters for re-selection in the UI
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("priorityFilter", priorityFilter);
        request.setAttribute("categoryFilter", categoryFilter);
        request.setAttribute("assigneeFilter", assigneeFilter);
        
        // Forward to task list page
        forwardToJsp(request, response, "/WEB-INF/tasks/taskList.jsp");
    }
    
    /**
     * Show task details
     */
    private void handleShowTaskDetails(HttpServletRequest request, HttpServletResponse response, int taskId) throws ServletException, IOException {
        Task task = taskService.getTaskById(taskId);
        
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Get creator and assignee users
        User creator = userService.getUserById(task.getCreatedBy());
        User assignee = userService.getUserById(task.getAssignedTo());
        
        // Get categories for this task
        List<Category> taskCategories = categoryService.getCategoriesByTaskId(taskId);
        
        // Set attributes
        request.setAttribute("task", task);
        request.setAttribute("creator", creator);
        request.setAttribute("assignee", assignee);
        request.setAttribute("taskCategories", taskCategories);
        
        // Forward to task details page
        forwardToJsp(request, response, "/WEB-INF/tasks/taskDetails.jsp");
    }
    
    /**
     * Show task edit form
     */
    private void handleShowEditForm(HttpServletRequest request, HttpServletResponse response, int taskId) throws ServletException, IOException {
        Task task = taskService.getTaskById(taskId);
        
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Get categories for this task
        List<Category> taskCategories = categoryService.getCategoriesByTaskId(taskId);
        List<Integer> taskCategoryIds = new ArrayList<>();
        for (Category category : taskCategories) {
            taskCategoryIds.add(category.getCategoryId());
        }
        
        // Set task and categories in request
        request.setAttribute("task", task);
        request.setAttribute("taskCategoryIds", taskCategoryIds);
        
        // Prepare form with users and categories
        prepareTaskForm(request);
        
        // Forward to task form page
        forwardToJsp(request, response, "/WEB-INF/tasks/taskForm.jsp");
    }
    
    /**
     * Create a new task
     */
    private void handleCreateTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = getAuthenticatedUser(request);
        
        try {
            // Parse request parameters
            Task task = parseTaskFromRequest(request);
            task.setCreatedBy(currentUser.getUserId());
            
            // Create the task
            task = taskService.createTask(task);
            
            if (task != null) {
                // Process categories
                String[] categoryIds = request.getParameterValues("categories");
                if (categoryIds != null) {
                    for (String categoryIdStr : categoryIds) {
                        try {
                            int categoryId = Integer.parseInt(categoryIdStr);
                            categoryService.addCategoryToTask(task.getTaskId(), categoryId);
                        } catch (NumberFormatException e) {
                            // Skip invalid category IDs
                        }
                    }
                }
                
                // Redirect to task details
                response.sendRedirect(request.getContextPath() + "/tasks/" + task.getTaskId());
            } else {
                throw new ServletException("Failed to create task");
            }
        } catch (Exception e) {
            // Re-populate form and show error
            prepareTaskForm(request);
            request.setAttribute("error", e.getMessage());
            forwardToJsp(request, response, "/WEB-INF/tasks/taskForm.jsp");
        }
    }
    
    /**
     * Update an existing task
     */
    private void handleUpdateTask(HttpServletRequest request, HttpServletResponse response, int taskId) throws ServletException, IOException {
        Task existingTask = taskService.getTaskById(taskId);
        
        if (existingTask == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            // Parse request parameters
            Task updatedTask = parseTaskFromRequest(request);
            updatedTask.setTaskId(taskId);
            updatedTask.setCreatedBy(existingTask.getCreatedBy());
            updatedTask.setCreatedAt(existingTask.getCreatedAt());
            
            // Update the task
            boolean success = taskService.updateTask(updatedTask);
            
            if (success) {
                // Process categories - first remove all existing categories
                List<Category> existingCategories = categoryService.getCategoriesByTaskId(taskId);
                for (Category category : existingCategories) {
                    categoryService.removeCategoryFromTask(taskId, category.getCategoryId());
                }
                
                // Add selected categories
                String[] categoryIds = request.getParameterValues("categories");
                if (categoryIds != null) {
                    for (String categoryIdStr : categoryIds) {
                        try {
                            int categoryId = Integer.parseInt(categoryIdStr);
                            categoryService.addCategoryToTask(taskId, categoryId);
                        } catch (NumberFormatException e) {
                            // Skip invalid category IDs
                        }
                    }
                }
                
                // Redirect to task details
                response.sendRedirect(request.getContextPath() + "/tasks/" + taskId);
            } else {
                throw new ServletException("Failed to update task");
            }
        } catch (Exception e) {
            // Re-populate form and show error
            request.setAttribute("task", existingTask);
            prepareTaskForm(request);
            request.setAttribute("error", e.getMessage());
            forwardToJsp(request, response, "/WEB-INF/tasks/taskForm.jsp");
        }
    }
    
    /**
     * Delete a task
     */
    private void handleDeleteTask(HttpServletRequest request, HttpServletResponse response, int taskId) throws IOException {
        try {
            boolean success = taskService.deleteTask(taskId);
            
            if (success) {
                sendSuccessResponse(response, "Task deleted successfully");
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete task");
            }
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    /**
     * Prepare the task form with necessary data
     */
    private void prepareTaskForm(HttpServletRequest request) {
        // Get all users for the assignee dropdown
        List<User> users = userService.getAllUsers();
        
        // Get all categories for the category dropdown
        List<Category> categories = categoryService.getAllCategories();
        
        // Set attributes
        request.setAttribute("users", users);
        request.setAttribute("categories", categories);
        request.setAttribute("statuses", Task.Status.values());
        request.setAttribute("priorities", Task.Priority.values());
    }
    
    /**
     * Parse task data from request parameters
     */
    private Task parseTaskFromRequest(HttpServletRequest request) throws Exception {
        Task task = new Task();
        
        // Basic fields
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");
        String priorityStr = request.getParameter("priority");
        String statusStr = request.getParameter("status");
        String assigneeIdStr = request.getParameter("assignedTo");
        
        // Validate required fields
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        
        task.setTitle(title);
        task.setDescription(description);
        
        // Parse and set due date
        if (dueDateStr != null && !dueDateStr.trim().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date dueDate = dateFormat.parse(dueDateStr);
                task.setDueDate(dueDate);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid due date format");
            }
        }
        
        // Parse and set priority
        if (priorityStr != null && !priorityStr.trim().isEmpty()) {
            try {
                Task.Priority priority = Task.Priority.valueOf(priorityStr);
                task.setPriority(priority);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid priority");
            }
        }
        
        // Parse and set status
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                Task.Status status = Task.Status.valueOf(statusStr);
                task.setStatus(status);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status");
            }
        }
        
        // Parse and set assignee
        if (assigneeIdStr != null && !assigneeIdStr.trim().isEmpty()) {
            try {
                int assigneeId = Integer.parseInt(assigneeIdStr);
                task.setAssignedTo(assigneeId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid assignee");
            }
        } else {
            throw new IllegalArgumentException("Assignee is required");
        }
        
        return task;
    }
}