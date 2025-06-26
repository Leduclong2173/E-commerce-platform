<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Delete</title>
        <link rel="stylesheet" />
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
                        <h1 class="mt-4">Delete</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">
                                <a href="/client">Dashboard</a>
                            </li>
                            <li class="breadcrumb-item active">
                                <a href="/client/product">Products</a>
                            </li>
                            <li class="breadcrumb-item active">Delete Product</li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-md6 col-12 mx-auto">
                                    <h3>Delete product with id = ${id}</h3>
                                    <hr />
                                    <div class="alert alert-danger">Are you sure to delete this product ?</div>
                                    <form:form method="post" action="/client/product/delete" modelAttribute="newProduct">
                                        <div class="mb-3" style="display: none">
                                            <label for="Id" class="form-label">Id</label>
                                            <form:input value="${id}" type="text" class="form-control" id="Id" path="product_id" />
                                        </div>
                                        <button class="btn btn-danger">Confirm</button>
                                        <a href="/client/product" class="btn btn-success">Back</a>
                                    </form:form>
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
