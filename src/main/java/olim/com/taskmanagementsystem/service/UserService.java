package olim.com.taskmanagementsystem.service;

import olim.com.taskmanagementsystem.model.User;
import java.util.List;

/**
 * Service interface for User-related operations
 */
public interface UserService {
    /**
     * Register a new user
     * 
     * @param user User to register
     * @return registered User with generated ID
     */
    User register(User user);
    
    /**
     * Get a user by ID
     * 
     * @param userId ID of the user to retrieve
     * @return User if found, null otherwise
     */
    User getUserById(int userId);
    
    /**
     * Get a user by username
     * 
     * @param username Username to search for
     * @return User if found, null otherwise
     */
    User getUserByUsername(String username);
    
    /**
     * Get all users
     * 
     * @return List of all users
     */
    List<User> getAllUsers();
    
    /**
     * Update a user's information
     * 
     * @param user User to update
     * @return true if updated successfully, false otherwise
     */
    boolean updateUser(User user);
    
    /**
     * Delete a user
     * 
     * @param userId ID of the user to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteUser(int userId);
    
    /**
     * Authenticate a user
     * 
     * @param username Username
     * @param password Password
     * @return User if authenticated, null otherwise
     */
    User authenticate(String username, String password);
}