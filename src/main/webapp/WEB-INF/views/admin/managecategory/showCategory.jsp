<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@taglibprefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Manage</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
            <jsp:include page="../layout/sidebar.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Manage Categories</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">
                                <a href="/admin">Dashboard</a>
                            </li>
                            <li class="breadcrumb-item active">Categories</li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>Table Category</h3>
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <!-- Search Form -->
                                            <form action="/admin/category/search" method="GET" class="d-flex" style="flex-grow: 1; max-width: 500px">
                                                <input
                                                    type="text"
                                                    name="keyword"
                                                    class="form-control me-2"
                                                    placeholder="Search by Name or ID"
                                                    value="${param.keyword}"
                                                />
                                                <button type="submit" class="btn btn-outline-primary">Search</button>
                                            </form>
                                            <a href="/admin/category/create" class="btn btn-primary ms-3">Create a category</a>
                                        </div>
                                    </div>
                                    <c:if test="${not empty message}">
                                        <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                                            <c:out value="${message}" />
                                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        </div>
                                    </c:if>
                                    <hr />
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="category" items="${categories}">
                                                <tr>
                                                    <th>${category.category_id}</th>
                                                    <td>${category.name}</td>
                                                    <td>${category.description}</td>
                                                    <td>
                                                        <a href="/admin/category/update/${category.category_id}" class="btn btn-warning mx-2"
                                                            >Update</a
                                                        >
                                                        <a href="/admin/category/delete/${category.category_id}" class="btn btn-danger">Delete</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="../layout/footer.jsp" />
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>
