package olim.com.taskmanagementsystem.model;

/**
 * TaskCategory model representing the many-to-many relationship between tasks and categories
 */
public class TaskCategory {
    private int taskId;
    private int categoryId;
    
    // Constructors
    public TaskCategory() {
    }
    
    public TaskCategory(int taskId, int categoryId) {
        this.taskId = taskId;
        this.categoryId = categoryId;
    }
    
    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    @Override
    public String toString() {
        return "TaskCategory [taskId=" + taskId + ", categoryId=" + categoryId + "]";
    }
}