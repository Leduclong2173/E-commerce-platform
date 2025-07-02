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
                        <h1 class="mt-4">Manage Users</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">
                                <a href="/admin">Dashboard</a>
                            </li>
                            <li class="breadcrumb-item active">Users</li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>Table user</h3>
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <!-- Search Form -->
                                            <form action="/admin/user/search" method="GET" class="d-flex" style="flex-grow: 1; max-width: 500px">
                                                <input
                                                    type="text"
                                                    name="keyword"
                                                    class="form-control me-2"
                                                    placeholder="Search by Username or ID"
                                                    value="${param.keyword}"
                                                />
                                                <button type="submit" class="btn btn-outline-primary">Search</button>
                                            </form>
                                            <a href="/admin/user/create" class="btn btn-primary ms-3">Create a user</a>
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
                                                <th>Username</th>
                                                <th>Name</th>
                                                <th>Role</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="user" items="${users}">
                                                <tr>
                                                    <th>${user.user_id}</th>
                                                    <td>${user.username}</td>
                                                    <td>${user.name}</td>
                                                    <td>${user.role.name}</td>
                                                    <td>
                                                        <a href="/admin/user/${user.user_id}" class="btn btn-success">View</a>
                                                        <a href="/admin/user/update/${user.user_id}" class="btn btn-warning mx-2">Update</a>
                                                        <a href="/admin/user/delete/${user.user_id}" class="btn btn-danger">Delete</a>
                                                        <!-- <c:if test="${user.role.role_id != 1}">
                                                            <a href="/admin/user/viewshop/${user.user_id}" class="btn btn-primary">View Shop</a>
                                                        </c:if> -->
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
