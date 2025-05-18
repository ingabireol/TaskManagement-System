<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Category Details" />
    <jsp:param name="active" value="categories" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Category Details</h1>
        <div>
            <a href="${pageContext.request.contextPath}/categories/${category.categoryId}/edit" class="btn btn-primary">
                <i class="bi bi-pencil"></i> Edit Category
            </a>
            <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back to Categories
            </a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <!-- Category Details Card -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">Category Information</h5>
                </div>
                <div class="card-body">
                    <h3 class="card-title">${category.name}</h3>

                    <div class="mb-4">
                        <p class="mb-1"><strong>Description:</strong></p>
                        <p class="card-text">${empty category.description ? 'No description provided.' : category.description}</p>
                    </div>

                    <div class="mt-3">
                        <a href="${pageContext.request.contextPath}/categories/${category.categoryId}/edit" class="btn btn-primary">
                            <i class="bi bi-pencil"></i> Edit Category
                        </a>
                        <button class="btn btn-danger delete-category" data-id="${category.categoryId}">
                            <i class="bi bi-trash"></i> Delete Category
                        </button>
                    </div>
                </div>
            </div>

            <!-- Tasks with this Category -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Tasks with this Category</h5>
                </div>
                <div class="card-body p-0">
                    <c:choose>
                        <c:when test="${empty categoryTasks}">
                            <div class="alert alert-info m-3">
                                No tasks are currently assigned to this category.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover mb-0">
                                    <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Title</th>
                                        <th>Status</th>
                                        <th>Priority</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${categoryTasks}" var="task">
                                        <tr>
                                            <td>${task.taskId}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/tasks/${task.taskId}">
                                                        ${task.title}
                                                </a>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${task.status == 'PENDING'}">
                                                        <span class="badge bg-warning">Pending</span>
                                                    </c:when>
                                                    <c:when test="${task.status == 'IN_PROGRESS'}">
                                                        <span class="badge bg-info">In Progress</span>
                                                    </c:when>
                                                    <c:when test="${task.status == 'COMPLETED'}">
                                                        <span class="badge bg-success">Completed</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${task.status}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${task.priority == 'LOW'}">
                                                        <span class="badge bg-secondary">Low</span>
                                                    </c:when>
                                                    <c:when test="${task.priority == 'MEDIUM'}">
                                                        <span class="badge bg-primary">Medium</span>
                                                    </c:when>
                                                    <c:when test="${task.priority == 'HIGH'}">
                                                        <span class="badge bg-warning">High</span>
                                                    </c:when>
                                                    <c:when test="${task.priority == 'URGENT'}">
                                                        <span class="badge bg-danger">Urgent</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${task.priority}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/tasks/${task.taskId}" class="btn btn-sm btn-info">
                                                    <i class="bi bi-eye"></i> View
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <!-- Quick Actions Card -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">Quick Actions</h5>
                </div>
                <div class="card-body">
                    <div class="d-grid gap-2">
                        <a href="${pageContext.request.contextPath}/categories/${category.categoryId}/edit" class="btn btn-primary">
                            <i class="bi bi-pencil"></i> Edit Category
                        </a>
                        <button class="btn btn-danger delete-category" data-id="${category.categoryId}">
                            <i class="bi bi-trash"></i> Delete Category
                        </button>
                        <a href="${pageContext.request.contextPath}/tasks/create" class="btn btn-success">
                            <i class="bi bi-plus"></i> Create Task with this Category
                        </a>
                    </div>
                </div>
            </div>

            <!-- Category Info Card -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Category Information</h5>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Category ID
                            <span class="badge bg-secondary rounded-pill">${category.categoryId}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Name
                            <span>${category.name}</span>
                        </li>
                        <c:if test="${not empty categoryTasks}">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Tasks Count
                                <span class="badge bg-primary rounded-pill">${categoryTasks.size()}</span>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />