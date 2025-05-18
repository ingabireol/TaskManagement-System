package olim.com.taskmanagementsystem.dao.impl;

import olim.com.taskmanagementsystem.dao.TaskDAO;
import olim.com.taskmanagementsystem.model.Task;
import olim.com.taskmanagementsystem.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of TaskDAO interface
 */
public class TaskDAOImpl implements TaskDAO {

    @Override
    public Task create(Task task) {
        String sql = "INSERT INTO tasks (title, description, due_date, priority, status, created_by, assigned_to, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING task_id";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDueDate() != null ? new Timestamp(task.getDueDate().getTime()) : null);
            stmt.setString(4, task.getPriority().toString());
            stmt.setString(5, task.getStatus().toString());
            stmt.setInt(6, task.getCreatedBy());
            stmt.setInt(7, task.getAssignedTo());
            stmt.setTimestamp(8, new Timestamp(task.getCreatedAt().getTime()));
            stmt.setTimestamp(9, new Timestamp(task.getUpdatedAt().getTime()));
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                task.setTaskId(rs.getInt("task_id"));
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return null;
    }

    @Override
    public Task getById(int taskId) {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractTaskFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return null;
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks ORDER BY due_date ASC";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }

    @Override
    public List<Task> getByAssignedUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_to = ? ORDER BY due_date ASC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }

    @Override
    public List<Task> getByCreatedUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE created_by = ? ORDER BY due_date ASC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }

    @Override
    public List<Task> getByStatus(Task.Status status) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE status = ? ORDER BY due_date ASC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status.toString());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }

    @Override
    public List<Task> getByPriority(Task.Priority priority) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE priority = ? ORDER BY due_date ASC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, priority.toString());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }

    @Override
    public List<Task> getByDueDateBefore(Date date) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE due_date <= ? ORDER BY due_date ASC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp(date.getTime()));
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }

    @Override
    public boolean update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, priority = ?, status = ?, " +
                     "assigned_to = ?, updated_at = ? WHERE task_id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDueDate() != null ? new Timestamp(task.getDueDate().getTime()) : null);
            stmt.setString(4, task.getPriority().toString());
            stmt.setString(5, task.getStatus().toString());
            stmt.setInt(6, task.getAssignedTo());
            stmt.setTimestamp(7, new Timestamp(new Date().getTime())); // Update the updated_at timestamp
            stmt.setInt(8, task.getTaskId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public boolean delete(int taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public List<Task> getByCategory(int categoryId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT t.* FROM tasks t " +
                     "JOIN task_categories tc ON t.task_id = tc.task_id " +
                     "WHERE tc.category_id = ? " +
                     "ORDER BY t.due_date ASC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeConnection(conn);
        }
        
        return tasks;
    }
    
    /**
     * Helper method to extract Task from ResultSet
     */
    private Task extractTaskFromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getInt("task_id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        
        Timestamp dueDate = rs.getTimestamp("due_date");
        if (dueDate != null) {
            task.setDueDate(new Date(dueDate.getTime()));
        }
        
        task.setPriority(Task.Priority.valueOf(rs.getString("priority")));
        task.setStatus(Task.Status.valueOf(rs.getString("status")));
        task.setCreatedBy(rs.getInt("created_by"));
        task.setAssignedTo(rs.getInt("assigned_to"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            task.setCreatedAt(new Date(createdAt.getTime()));
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            task.setUpdatedAt(new Date(updatedAt.getTime()));
        }
        
        return task;
    }
}