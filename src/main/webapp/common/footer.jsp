</main>
</div>
</div>
</div>

<footer class="bg-dark text-light py-3 mt-auto">
    <div class="container text-center">
        <p class="mb-0">&copy; 2025 NovaTech Solutions - Task Management System</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    // Common JavaScript functionality
    $(document).ready(function() {
        // Handle task deletion
        $('.delete-task').click(function(e) {
            e.preventDefault();
            if (confirm('Are you sure you want to delete this task?')) {
                const taskId = $(this).data('id');
                $.ajax({
                    url: '${pageContext.request.contextPath}/tasks/' + taskId,
                    type: 'DELETE',
                    success: function(result) {
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert('Error: ' + xhr.responseJSON.error);
                    }
                });
            }
        });

        // Handle category deletion
        $('.delete-category').click(function(e) {
            e.preventDefault();
            if (confirm('Are you sure you want to delete this category?')) {
                const categoryId = $(this).data('id');
                $.ajax({
                    url: '${pageContext.request.contextPath}/categories/' + categoryId,
                    type: 'DELETE',
                    success: function(result) {
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert('Error: ' + xhr.responseJSON.error);
                    }
                });
            }
        });
    });
</script>
</body>
</html>