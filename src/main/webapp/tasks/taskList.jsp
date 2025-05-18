<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Tasks" />
    <jsp:param name="active" value="tasks" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Tasks</h1>
        <a href="${pageContext.request.contextPath}/tasks/create" class="btn btn-primary">
            <i class="bi bi-plus"></i> New Task
        </a>
    </div>

    <!-- Filters -->
    <div class="card mb-4">
        <div class="card-header bg-light">
            <h5 class="mb-0">Filters</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/tasks" method="get" class="row g-3">
                <div class="col-md-3">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select" id="status" name="status">
                        <option value="">All Statuses</option>
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${statusFilter eq status ? 'selected' : ''}>
                                    ${status}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="priority" class="form-label">Priority</label>
                    <select class="form-select" id="priority" name="priority">
                        <option value="">All Priorities</option>
                        <c:forEach items="${priorities}" var="priority">
                            <option value="${priority}" ${priorityFilter eq priority ? 'selected' : ''}>
                                    ${priority}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="category" class="form-label">Category</label>
                    <select class="form-select" id="category" name="category">
                        <option value="">All Categories</option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryId}" ${categoryFilter eq category.categoryId ? 'selected' : ''}>
                                    ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="assignee" class="form-label">Assignee</label>
                    <select class="form-select" id="assignee" name="assignee">
                        <option value="">All Assignees</option>
                        <c:forEach items="${users}" var="user">
                            <option value="${user.userId}" ${assigneeFilter eq user.userId ? 'selected' : ''}>
                                    ${user.fullName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Apply Filters</button>
                    <a href="${pageContext.request.contextPath}/tasks" class="btn btn-secondary">Clear Filters</a>
                </div>
            </form>
        </div>
    </div>

    <!-- Task List -->
    <div class="card">
        <div class="card-header bg-light">
            <h5 class="mb-0">Task List</h5>
        </div>
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty tasks}">
                    <div class="alert alert-info m-3">
                        No tasks found. <a href="${pageContext.request.contextPath}/tasks/create">Create a new task</a>.
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
                                <th>Due Date</th>
                                <th>Assignee</th>
                                <th>Categories</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tasks}" var="task">
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
                                        <c:if test="${not empty task.dueDate}">
                                            <fmt:formatDate value="${task.dueDate}" pattern="yyyy-MM-dd" />
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:forEach items="${users}" var="user">
                                            <c:if test="${user.userId == task.assignedTo}">
                                                ${user.fullName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:set var="taskCategories" value="" />
                                        <c:forEach items="${categories}" var="category">
                                            <c:forEach items="${task.categories}" var="taskCategory">
                                                <c:if test="${category.categoryId == taskCategory.categoryId}">
                                                    <span class="badge bg-dark">${category.name}</span>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm">
                                            <a href="${pageContext.request.contextPath}/tasks/${task.taskId}" class="btn btn-info">
                                                <i class="bi bi-eye"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/tasks/${task.taskId}/edit" class="btn btn-primary">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <button class="btn btn-danger delete-task" data-id="${task.taskId}">
                                                <i class="bi bi-trash"></i>
                                            </button>
                                        </div>
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

<jsp:include page="../common/footer.jsp" />