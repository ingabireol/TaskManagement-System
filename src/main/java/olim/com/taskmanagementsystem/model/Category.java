package olim.com.taskmanagementsystem.model;

/**
 * Category model representing a task classification
 */
public class Category {
    private int categoryId;
    private String name;
    private String description;
    
    // Constructors
    public Category() {
    }
    
    public Category(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }
    
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", name=" + name + "]";
    }
}