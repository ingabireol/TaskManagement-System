<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Task Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        main {
            flex: 1;
        }
        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
        }
        .sidebar {
            background-color: #212529;
            color: white;
            min-height: calc(100vh - 56px);
            position: fixed;
            top: 56px;
            left: 0;
            width: 220px;
            padding-top: 20px;
        }
        .sidebar a {
            color: rgba(255, 255, 255, 0.8);
            text-decoration: none;
            padding: 10px 15px;
            display: block;
        }
        .sidebar a:hover {
            color: #fff;
            background-color: rgba(255, 255, 255, 0.1);
        }
        .sidebar a.active {
            color: #fff;
            background-color: #0d6efd;
        }
        .sidebar-heading {
            padding: 10px 15px;
            font-size: 0.8rem;
            text-transform: uppercase;
            opacity: 0.6;
        }
        .content {
            margin-left: 220px;
            padding: 20px;
        }
        .navbar-user {
            display: flex;
            align-items: center;
            color: white;
        }
        .navbar-user-name {
            margin-right: 10px;
        }

        @media (max-width: 768px) {
            .sidebar {
                width: 100%;
                position: static;
                min-height: auto;
            }
            .content {
                margin-left: 0;
            }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/tasks">Task Management System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item navbar-user">
                    <span class="navbar-user-name">${sessionScope.user.fullName}</span>
                    <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid p-0">
<div class="row g-0">
<div class="col-md-3 col-lg-2">
    <div class="sidebar">
        <div class="sidebar-heading">Main</div>
        <a href="${pageContext.request.contextPath}/tasks" class="${param.active == 'tasks' ? 'active' : ''}">
            <i class="bi bi-list-task me-2"></i> Tasks
        </a>
        <a href="${pageContext.request.contextPath}/categories" class="${param.active == 'categories' ? 'active' : ''}">
            <i class="bi bi-tag me-2"></i> Categories
        </a>

        <div class="sidebar-heading">Actions</div>
        <a href="${pageContext.request.contextPath}/tasks/create">
            <i class="bi bi-plus-circle me-2"></i> New Task
        </a>
        <a href="${pageContext.request.contextPath}/categories/create">
            <i class="bi bi-plus-circle me-2"></i> New Category
        </a>
    </div>
</div>
<div class="col-md-9 col-lg-10">
<main class="content">
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            ${error}
    </div>
</c:if>
<c:if test="${not empty success}">
    <div class="alert alert-success" role="alert">
            ${success}
    </div>
</c:if>