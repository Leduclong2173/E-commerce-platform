<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>ViewShop</title>
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
                        <h1 class="mt-4">View Products</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">
                                <a href="/admin">Dashboard</a>
                            </li>
                            <li class="breadcrumb-item active"><a href="/admin/user">Users</a></li>
                            <li class="breadcrumb-item active">View</li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>Table product</h3>
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <!-- Search Form -->
                                            <form
                                                action="/admin/user/viewshop/search"
                                                method="GET"
                                                class="d-flex"
                                                style="flex-grow: 1; max-width: 500px"
                                            >
                                                <input
                                                    type="text"
                                                    name="keyword"
                                                    class="form-control me-2"
                                                    placeholder="Search by Product name or ID"
                                                    value="${param.keyword}"
                                                />
                                                <button type="submit" class="btn btn-outline-primary">Search</button>
                                            </form>
                                        </div>
                                    </div>
                                    <hr />
                                    <c:choose>
                                        <c:when test="${empty products}">
                                            <div class="alert alert-warning text-center" role="alert">No products available for this user.</div>
                                            <a href="/admin/user" class="btn btn-success mt-3">Back</a>
                                        </c:when>
                                        <c:otherwise>
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Product name</th>
                                                        <th>Price</th>
                                                        <th>Stock</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="product" items="${products}">
                                                        <tr>
                                                            <th>${product.product_id}</th>
                                                            <td>${product.name}</td>
                                                            <td><fmt:formatNumber type="number" value="${product.price}" /> đ</td>
                                                            <td>${product.stock}</td>
                                                            <td>
                                                                <a href="/admin/user/viewshopdetail/${product.product_id}" class="btn btn-success"
                                                                    >View</a
                                                                >
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="/admin/user" class="btn btn-success mt-3">Back</a>
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
