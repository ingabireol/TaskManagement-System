package olim.com.taskmanagementsystem.service.impl;

import olim.com.taskmanagementsystem.dao.UserDAO;
import olim.com.taskmanagementsystem.dao.impl.UserDAOImpl;
import olim.com.taskmanagementsystem.model.User;
import olim.com.taskmanagementsystem.service.UserService;

import java.util.List;

/**
 * Implementation of UserService interface
 */
public class UserServiceImpl implements UserService {
    
    private final UserDAO userDAO;
    
    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }
    
    @Override
    public User register(User user) {
        // Check if username already exists
        if (userDAO.getByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        // Check if email already exists
        if (userDAO.getByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Validate user data
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        // In a real application, you would hash the password before storing it
        // For simplicity, we're not doing that here
        
        return userDAO.create(user);
    }
    
    @Override
    public User getUserById(int userId) {
        return userDAO.getById(userId);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userDAO.getByUsername(username);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    
    @Override
    public boolean updateUser(User user) {
        // Validate user data
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        
        // Check if username is taken by another user
        User existingUser = userDAO.getByUsername(user.getUsername());
        if (existingUser != null && existingUser.getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        // Check if email is taken by another user
        existingUser = userDAO.getByEmail(user.getEmail());
        if (existingUser != null && existingUser.getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        return userDAO.update(user);
    }
    
    @Override
    public boolean deleteUser(int userId) {
        return userDAO.delete(userId);
    }
    
    @Override
    public User authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        // In a real application, you would hash the password before comparing
        return userDAO.authenticate(username, password);
    }
}