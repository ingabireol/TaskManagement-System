-- Drop tables if they exist
DROP TABLE IF EXISTS task_categories;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       full_name VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL
);

-- Create categories table
CREATE TABLE categories (
                            category_id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE,
                            description TEXT
);

-- Create tasks table
CREATE TABLE tasks (
                       task_id SERIAL PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       due_date TIMESTAMP,
                       priority VARCHAR(20) NOT NULL,
                       status VARCHAR(20) NOT NULL,
                       created_by INTEGER NOT NULL REFERENCES users(user_id),
                       assigned_to INTEGER NOT NULL REFERENCES users(user_id),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create task_categories table (many-to-many relationship)
CREATE TABLE task_categories (
                                 task_id INTEGER NOT NULL REFERENCES tasks(task_id) ON DELETE CASCADE,
                                 category_id INTEGER NOT NULL REFERENCES categories(category_id) ON DELETE CASCADE,
                                 PRIMARY KEY (task_id, category_id)
);

-- Insert some sample users
INSERT INTO users (username, email, full_name, password) VALUES
                                                             ('admin', 'admin@novatech.com', 'Admin User', 'admin123'),
                                                             ('ingabire', 'olivier@novatech.com', 'Ingabire Olivier', 'john123'),
                                                             ('olivier', 'humurae@novatech.com', 'humura', 'jane123');

-- Insert some sample categories
INSERT INTO categories (name, description) VALUES
                                               ('Frontend', 'Frontend development tasks'),
                                               ('Backend', 'Backend development tasks'),
                                               ('UI/UX', 'User interface and experience design tasks'),
                                               ('Testing', 'Quality assurance and testing tasks'),
                                               ('DevOps', 'Development operations tasks'),
                                               ('Documentation', 'Documentation and specification tasks');

-- Insert some sample tasks
INSERT INTO tasks (title, description, due_date, priority, status, created_by, assigned_to, created_at, updated_at) VALUES
                                                                                                                        ('Implement login page', 'Create a responsive login page with validation', '2025-06-15 12:00:00', 'HIGH', 'PENDING', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                        ('Set up database schema', 'Design and implement database schema for the project', '2025-06-10 12:00:00', 'URGENT', 'IN_PROGRESS', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                        ('Create API documentation', 'Document all REST endpoints for the project', '2025-06-20 12:00:00', 'MEDIUM', 'PENDING', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                        ('Implement unit tests', 'Create comprehensive unit tests for backend services', '2025-06-25 12:00:00', 'HIGH', 'PENDING', 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                        ('Configure CI/CD pipeline', 'Set up continuous integration and deployment pipeline', '2025-06-30 12:00:00', 'MEDIUM', 'PENDING', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign categories to tasks
INSERT INTO task_categories (task_id, category_id) VALUES
                                                       (1, 1), -- Login page - Frontend
                                                       (1, 3), -- Login page - UI/UX
                                                       (2, 2), -- Database schema - Backend
                                                       (3, 6), -- API documentation - Documentation
                                                       (4, 4), -- Unit tests - Testing
                                                       (5, 5); -- CI/CD pipeline - DevOps