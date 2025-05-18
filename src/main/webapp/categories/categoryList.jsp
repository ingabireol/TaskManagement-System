<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Categories" />
    <jsp:param name="active" value="categories" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Categories</h1>
        <a href="${pageContext.request.contextPath}/categories/create" class="btn btn-primary">
            <i class="bi bi-plus"></i> New Category
        </a>
    </div>

    <div class="card">
        <div class="card-header bg-light">
            <h5 class="mb-0">Category List</h5>
        </div>
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty categories}">
                    <div class="alert alert-info m-3">
                        No categories found. <a href="${pageContext.request.contextPath}/categories/create">Create a new category</a>.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover mb-0">
                            <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${categories}" var="category">
                                <tr>
                                    <td>${category.categoryId}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/categories/${category.categoryId}">
                                                ${category.name}
                                        </a>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty category.description}">
                                                <span class="text-muted">No description</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${category.description}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm">
                                            <a href="${pageContext.request.contextPath}/categories/${category.categoryId}" class="btn btn-info">
                                                <i class="bi bi-eye"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/categories/${category.categoryId}/edit" class="btn btn-primary">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <button class="btn btn-danger delete-category" data-id="${category.categoryId}">
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