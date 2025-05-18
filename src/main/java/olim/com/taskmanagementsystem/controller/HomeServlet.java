package olim.com.taskmanagementsystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet for handling home page requests
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"", "/home"})
public class HomeServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If user is authenticated, redirect to tasks page
        if (isAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/tasks");
        } else {
            // Otherwise, redirect to login page
            response.sendRedirect(request.getContextPath() + "/auth/login");
        }
    }
}