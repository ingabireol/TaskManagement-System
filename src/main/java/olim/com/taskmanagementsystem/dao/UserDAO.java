package olim.com.taskmanagementsystem.dao;

import olim.com.taskmanagementsystem.model.User;
import java.util.List;

/**
 * Data Access Object interface for User entity
 */
public interface UserDAO {
    /**
     * Create a new user
     * 
     * @param user User to create
     * @return created User with generated ID
     */
    User create(User user);
    
    /**
     * Get a user by ID
     * 
     * @param userId ID of the user to retrieve
     * @return User if found, null otherwise
     */
    User getById(int userId);
    
    /**
     * Get a user by username
     * 
     * @param username Username to search for
     * @return User if found, null otherwise
     */
    User getByUsername(String username);
    
    /**
     * Get a user by email
     * 
     * @param email Email to search for
     * @return User if found, null otherwise
     */
    User getByEmail(String email);
    
    /**
     * Get all users
     * 
     * @return List of all users
     */
    List<User> getAll();
    
    /**
     * Update a user
     * 
     * @param user User to update
     * @return true if updated successfully, false otherwise
     */
    boolean update(User user);
    
    /**
     * Delete a user
     * 
     * @param userId ID of the user to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean delete(int userId);
    
    /**
     * Authenticate a user
     * 
     * @param username Username
     * @param password Password
     * @return User if authenticated, null otherwise
     */
    User authenticate(String username, String password);
}