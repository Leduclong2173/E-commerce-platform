<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Details</title>
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
                        <h1 class="mt-4">Product Details</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/shop">Dashboard</a></li>
                            <li class="breadcrumb-item"><a href="/shop/product">Products</a></li>
                            <li class="breadcrumb-item active">Detail</li>
                        </ol>
                        <div class="container mt-5">
                            <div class="row">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>${product.name}</h3>
                                    </div>
                                    <hr />
                                    <div class="card" style="width: 60%">
                                        <img style="width: 40vh" class="card-img-top" src="/images/product/${product.image}" alt="product image" />
                                        <div class="card-header">Product information</div>
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">ID: ${product.product_id}</li>
                                            <li class="list-group-item">Name: ${product.name}</li>
                                            <li class="list-group-item">Category: ${product.category.name}</li>
                                            <li class="list-group-item">Price: <fmt:formatNumber type="number" value="${product.price}" /> đ</li>
                                            <li class="list-group-item">Short description ${product.shortDesc}</li>
                                            <li class="list-group-item">Detail description: ${product.detailDesc}</li>
                                            <li class="list-group-item">Stock: ${product.stock}</li>
                                        </ul>
                                    </div>
                                    <a href="/shop/product" class="btn btn-success mt-3">Back</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="../layout/footer.jsp" />
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/js/scripts.js"></script>
    </body>
</html>
