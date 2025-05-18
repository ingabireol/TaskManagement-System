package olim.com.taskmanagementsystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import olim.com.taskmanagementsystem.model.User;
import olim.com.taskmanagementsystem.service.UserService;
import olim.com.taskmanagementsystem.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet for handling authentication (login, logout, registration)
 */
@WebServlet(name = "AuthServlet", urlPatterns = {"/auth/*"})
public class AuthServlet extends BaseServlet {
    
    private final UserService userService;
    
    public AuthServlet() {
        this.userService = new UserServiceImpl();
    }
    
    /**
     * Handle GET requests - show login or registration form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        switch (pathInfo) {
            case "/login":
                // If already logged in, redirect to dashboard
                if (isAuthenticated(request)) {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                    return;
                }
                forwardToJsp(request, response, "/WEB-INF/auth/login.jsp");
                break;
                
            case "/register":
                // If already logged in, redirect to dashboard
                if (isAuthenticated(request)) {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                    return;
                }
                forwardToJsp(request, response, "/WEB-INF/auth/register.jsp");
                break;
                
            case "/logout":
                // Invalidate session and redirect to login
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/auth/login");
                break;
                
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
    
    /**
     * Handle POST requests - process login or registration
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        switch (pathInfo) {
            case "/login":
                handleLogin(request, response);
                break;
                
            case "/register":
                handleRegistration(request, response);
                break;
                
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
    
    /**
     * Handle login request
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            forwardToJsp(request, response, "/WEB-INF/auth/login.jsp");
            return;
        }
        
        try {
            User user = userService.authenticate(username, password);
            
            if (user != null) {
                // Set user in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                // Determine if this is an AJAX request
                String acceptHeader = request.getHeader("Accept");
                if (acceptHeader != null && acceptHeader.contains("application/json")) {
                    // Respond with JSON
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("success", true);
                    responseMap.put("redirect", request.getContextPath() + "/tasks");
                    
                    sendJsonResponse(response, responseMap);
                } else {
                    // Redirect to tasks page
                    response.sendRedirect(request.getContextPath() + "/tasks");
                }
            } else {
                if (request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json")) {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    forwardToJsp(request, response, "/WEB-INF/auth/login.jsp");
                }
            }
        } catch (Exception e) {
            if (request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json")) {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } else {
                request.setAttribute("error", e.getMessage());
                forwardToJsp(request, response, "/WEB-INF/auth/login.jsp");
            }
        }
    }
    
    /**
     * Handle registration request
     */
    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        boolean hasErrors = false;
        
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username is required");
            hasErrors = true;
        }
        
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required");
            hasErrors = true;
        }
        
        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("fullNameError", "Full name is required");
            hasErrors = true;
        }
        
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required");
            hasErrors = true;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match");
            hasErrors = true;
        }
        
        if (hasErrors) {
            // Preserve user input
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            
            forwardToJsp(request, response, "/WEB-INF/auth/register.jsp");
            return;
        }
        
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setPassword(password);
            
            user = userService.register(user);
            
            if (user != null) {
                // Set success message and redirect to login
                request.getSession().setAttribute("registrationSuccess", "Registration successful. Please login.");
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                forwardToJsp(request, response, "/WEB-INF/auth/register.jsp");
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            
            // Preserve user input
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            
            forwardToJsp(request, response, "/WEB-INF/auth/register.jsp");
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            
            // Preserve user input
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            
            forwardToJsp(request, response, "/WEB-INF/auth/register.jsp");
        }
    }
}