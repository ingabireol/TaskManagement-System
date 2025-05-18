package olim.com.taskmanagementsystem.model;

import java.util.Date;

/**
 * Task model representing a work item in the system
 */
public class Task {
    
    // Enum for task status
    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED
    }
    
    // Enum for task priority
    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
    
    private int taskId;
    private String title;
    private String description;
    private Date dueDate;
    private Priority priority;
    private Status status;
    private int createdBy;  // User ID of creator
    private int assignedTo; // User ID of assignee
    private Date createdAt;
    private Date updatedAt;
    
    // Constructors
    public Task() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = Status.PENDING;
        this.priority = Priority.MEDIUM;
    }
    
    public Task(String title, String description, Date dueDate, int createdBy, int assignedTo) {
        this();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }
    
    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public int getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", title=" + title + ", status=" + status + 
               ", priority=" + priority + ", dueDate=" + dueDate + "]";
    }
}