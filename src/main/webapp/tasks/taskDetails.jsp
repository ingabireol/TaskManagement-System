<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Task Details" />
    <jsp:param name="active" value="tasks" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Task Details</h1>
        <div>
            <a href="${pageContext.request.contextPath}/tasks/${task.taskId}/edit" class="btn btn-primary">
                <i class="bi bi-pencil"></i> Edit Task
            </a>
            <a href="${pageContext.request.contextPath}/tasks" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back to Tasks
            </a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <!-- Task Details Card -->
            <div class="card mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Task Information</h5>
                    <div>
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
                        <c:choose>
                            <c:when test="${task.priority == 'LOW'}">
                                <span class="badge bg-secondary">Low Priority</span>
                            </c:when>
                            <c:when test="${task.priority == 'MEDIUM'}">
                                <span class="badge bg-primary">Medium Priority</span>
                            </c:when>
                            <c:when test="${task.priority == 'HIGH'}">
                                <span class="badge bg-warning">High Priority</span>
                            </c:when>
                            <c:when test="${task.priority == 'URGENT'}">
                                <span class="badge bg-danger">Urgent Priority</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-secondary">${task.priority} Priority</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="card-body">
                    <h3 class="card-title">${task.title}</h3>

                    <div class="mb-4">
                        <p class="mb-1"><strong>Description:</strong></p>
                        <p class="card-text">${empty task.description ? 'No description provided.' : task.description}</p>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="mb-1"><strong>Assigned To:</strong></p>
                            <p>${assignee.fullName}</p>
                        </div>
                        <div class="col-md-6">
                            <p class="mb-1"><strong>Created By:</strong></p>
                            <p>${creator.fullName}</p>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="mb-1"><strong>Due Date:</strong></p>
                            <p>
                                <c:if test="${not empty task.dueDate}">
                                    <fmt:formatDate value="${task.dueDate}" pattern="yyyy-MM-dd HH:mm" />
                                </c:if>
                                <c:if test="${empty task.dueDate}">
                                    No due date set
                                </c:if>
                            </p>
                        </div>
                        <div class="col-md-6">
                            <p class="mb-1"><strong>Categories:</strong></p>
                            <p>
                                <c:if test="${empty taskCategories}">
                                    No categories assigned
                                </c:if>
                                <c:forEach items="${taskCategories}" var="category" varStatus="status">
                                    <span class="badge bg-dark">${category.name}</span>
                                </c:forEach>
                            </p>
                        </div>
                    </div>

                    <div class="mt-3">
                        <button class="btn btn-danger delete-task" data-id="${task.taskId}">
                            <i class="bi bi-trash"></i> Delete Task
                        </button>
                    </div>
                </div>
                <div class="card-footer text-muted">
                    <div class="row">
                        <div class="col-md-6">
                            <small>Created: <fmt:formatDate value="${task.createdAt}" pattern="yyyy-MM-dd HH:mm" /></small>
                        </div>
                        <div class="col-md-6 text-end">
                            <small>Last Updated: <fmt:formatDate value="${task.updatedAt}" pattern="yyyy-MM-dd HH:mm" /></small>
                        </div>
                    </div>
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
                        <a href="${pageContext.request.contextPath}/tasks/${task.taskId}/edit" class="btn btn-primary">
                            <i class="bi bi-pencil"></i> Edit Task
                        </a>

                        <c:choose>
                            <c:when test="${task.status == 'PENDING'}">
                                <form action="${pageContext.request.contextPath}/tasks/${task.taskId}" method="post" class="d-inline">
                                    <input type="hidden" name="status" value="IN_PROGRESS">
                                    <button type="submit" class="btn btn-info w-100">
                                        <i class="bi bi-play-fill"></i> Start Task
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${task.status == 'IN_PROGRESS'}">
                                <form action="${pageContext.request.contextPath}/tasks/${task.taskId}" method="post" class="d-inline">
                                    <input type="hidden" name="status" value="COMPLETED">
                                    <button type="submit" class="btn btn-success w-100">
                                        <i class="bi bi-check-lg"></i> Complete Task
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${task.status == 'COMPLETED'}">
                                <form action="${pageContext.request.contextPath}/tasks/${task.taskId}" method="post" class="d-inline">
                                    <input type="hidden" name="status" value="IN_PROGRESS">
                                    <button type="submit" class="btn btn-warning w-100">
                                        <i class="bi bi-arrow-repeat"></i> Reopen Task
                                    </button>
                                </form>
                            </c:when>
                        </c:choose>

                        <button class="btn btn-danger delete-task" data-id="${task.taskId}">
                            <i class="bi bi-trash"></i> Delete Task
                        </button>
                    </div>
                </div>
            </div>

            <!-- Timestamp Info Card -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Task Information</h5>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Task ID
                            <span class="badge bg-secondary rounded-pill">${task.taskId}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Status
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
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Priority
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
                        </li>
                        <li class="list-group-item">
                            <small>Created: <fmt:formatDate value="${task.createdAt}" pattern="yyyy-MM-dd HH:mm" /></small>
                        </li>
                        <li class="list-group-item">
                            <small>Last Updated: <fmt:formatDate value="${task.updatedAt}" pattern="yyyy-MM-dd HH:mm" /></small>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />