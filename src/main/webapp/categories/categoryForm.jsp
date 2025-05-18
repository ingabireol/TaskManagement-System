<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${empty category ? 'Create Category' : 'Edit Category'}" />
    <jsp:param name="active" value="categories" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>${empty category ? 'Create New Category' : 'Edit Category'}</h1>
        <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Back to Categories
        </a>
    </div>

    <div class="card">
        <div class="card-header bg-light">
            <h5 class="mb-0">${empty category ? 'Category Information' : 'Edit Category: '.concat(category.name)}</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/categories${empty category ? '/create' : '/'.concat(category.categoryId)}" method="post">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${category.name}" required>
                    <div class="form-text">Enter a short, descriptive name for the category.</div>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3">${category.description}</textarea>
                    <div class="form-text">Provide a brief description of the category (optional).</div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-lg"></i> ${empty category ? 'Create Category' : 'Update Category'}
                    </button>
                    <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
                        <i class="bi bi-x-lg"></i> Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />