package olim.com.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import olim.com.taskmanagementsystem.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Base abstract servlet class that provides common functionality for all servlets
 */
public abstract class BaseServlet extends HttpServlet {
    
    protected final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Check if the user is authenticated
     * 
     * @param request HTTP request
     * @return true if authenticated, false otherwise
     */
    protected boolean isAuthenticated(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user != null;
    }
    
    /**
     * Get the authenticated user
     * 
     * @param request HTTP request
     * @return authenticated User or null if not authenticated
     */
    protected User getAuthenticatedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
    
    /**
     * Send a JSON response
     * 
     * @param response HTTP response
     * @param object Object to send as JSON
     * @throws IOException if an I/O error occurs
     */
    protected void sendJsonResponse(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(object));
        out.flush();
    }
    
    /**
     * Send an error response
     * 
     * @param response HTTP response
     * @param status HTTP status code
     * @param message Error message
     * @throws IOException if an I/O error occurs
     */
    protected void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", message);
        
        sendJsonResponse(response, errorMap);
    }
    
    /**
     * Read the request body as JSON and convert it to the specified class
     * 
     * @param request HTTP request
     * @param clazz Class to convert to
     * @param <T> Type parameter
     * @return Object of the specified class
     * @throws IOException if an I/O error occurs
     */
    protected <T> T readRequestBodyAsJson(HttpServletRequest request, Class<T> clazz) throws IOException {
        return objectMapper.readValue(request.getInputStream(), clazz);
    }
    
    /**
     * Handle authentication for protected resources
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @return true if authenticated, false otherwise
     * @throws IOException if an I/O error occurs
     */
    protected boolean handleAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isAuthenticated(request)) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
            return false;
        }
        return true;
    }
    
    /**
     * Send a success response
     * 
     * @param response HTTP response
     * @param message Success message
     * @throws IOException if an I/O error occurs
     */
    protected void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        Map<String, String> successMap = new HashMap<>();
        successMap.put("message", message);
        
        sendJsonResponse(response, successMap);
    }
    
    /**
     * Forward to a JSP page
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param jspPath Path to the JSP page
     * @throws ServletException if the request could not be handled
     * @throws IOException if an I/O error occurs
     */
    protected void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String jspPath) 
            throws ServletException, IOException {
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}