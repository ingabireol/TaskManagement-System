<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${empty task ? 'Create Task' : 'Edit Task'}" />
    <jsp:param name="active" value="tasks" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>${empty task ? 'Create New Task' : 'Edit Task'}</h1>
        <a href="${pageContext.request.contextPath}/tasks" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Back to Tasks
        </a>
    </div>

    <div class="card">
        <div class="card-header bg-light">
            <h5 class="mb-0">${empty task ? 'Task Information' : 'Edit Task: '.concat(task.title)}</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/tasks${empty task ? '/create' : '/'.concat(task.taskId)}" method="post">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title"
                               value="${task.title}" required>
                    </div>
                    <div class="col-md-6">
                        <label for="dueDate" class="form-label">Due Date</label>
                        <input type="datetime-local" class="form-control" id="dueDate" name="dueDate"
                        <c:if test="${not empty task.dueDate}">
                               value="<fmt:formatDate value="${task.dueDate}" pattern="yyyy-MM-dd'T'HH:mm" />"
                        </c:if>
                        >
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-4">
                        <label for="status" class="form-label">Status</label>
                        <select class="form-select" id="status" name="status" required>
                            <c:forEach items="${statuses}" var="status">
                                <option value="${status}" ${task.status eq status ? 'selected' : ''}>
                                        ${status}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="priority" class="form-label">Priority</label>
                        <select class="form-select" id="priority" name="priority" required>
                            <c:forEach items="${priorities}" var="priority">
                                <option value="${priority}" ${task.priority eq priority ? 'selected' : ''}>
                                        ${priority}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="assignedTo" class="form-label">Assignee</label>
                        <select class="form-select" id="assignedTo" name="assignedTo" required>
                            <option value="">Select Assignee</option>
                            <c:forEach items="${users}" var="user">
                                <option value="${user.userId}" ${task.assignedTo eq user.userId ? 'selected' : ''}>
                                        ${user.fullName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="4">${task.description}</textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Categories</label>
                    <div class="row">
                        <c:forEach items="${categories}" var="category">
                            <div class="col-md-3 mb-2">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox"
                                           name="categories" value="${category.categoryId}" id="category${category.categoryId}"
                                           <c:if test="${not empty taskCategoryIds and taskCategoryIds.contains(category.categoryId)}">checked</c:if>>
                                    <label class="form-check-label" for="category${category.categoryId}">
                                            ${category.name}
                                    </label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-lg"></i> ${empty task ? 'Create Task' : 'Update Task'}
                    </button>
                    <a href="${pageContext.request.contextPath}/tasks" class="btn btn-secondary">
                        <i class="bi bi-x-lg"></i> Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />