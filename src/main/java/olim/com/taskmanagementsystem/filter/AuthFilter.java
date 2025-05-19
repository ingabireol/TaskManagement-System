package olim.com.taskmanagementsystem.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import olim.com.taskmanagementsystem.model.User;

import java.io.IOException;

/**
 * Filter to check if a user is authenticated before accessing protected resources
 */
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Check if the user is authenticated
        HttpSession session = httpRequest.getSession(false);
        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
        
        // If not authenticated, redirect to login page
        if (!isAuthenticated) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
        } else {
            // If authenticated, continue with the request
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy() {
    }
}