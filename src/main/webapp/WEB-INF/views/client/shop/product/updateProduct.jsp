<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Update</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </head>
    <script>
        $(document).ready(() => {
            const productFile = $("#productFile");
            const orgImage = "${updateProduct.image}";

            if (orgImage && orgImage !== "") {
                const urlImage = "/images/product/" + orgImage;
                $("#productPreview").attr("src", urlImage);
                $("#productPreview").css({ display: "block" });
            }

            productFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#productPreview").attr("src", imgURL);
                $("#productPreview").css({ display: "block" });
            });
        });
    </script>

    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
            <jsp:include page="../layout/sidebar.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Update</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/shop">Dashboard</a></li>
                            <li class="breadcrumb-item"><a href="/shop/product">Products</a></li>
                            <li class="breadcrumb-item active">Update</li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto">
                                    <h3>${product.name}</h3>
                                    <hr />
                                    <form:form
                                        method="post"
                                        action="/shop/product/update"
                                        class="row"
                                        enctype="multipart/form-data"
                                        modelAttribute="updateProduct"
                                    >
                                        <c:set var="errorName">
                                            <form:errors path="name" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorCategory">
                                            <form:errors path="category" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorPrice">
                                            <form:errors path="price" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorDetailDesc">
                                            <form:errors path="detailDesc" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorShortDesc">
                                            <form:errors path="shortDesc" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorStock">
                                            <form:errors path="stock" cssClass="invalid-feedback" />
                                        </c:set>
                                        <div class="mb-3" style="display: none">
                                            <label class="form-label">Id</label>
                                            <form:input type="text" class="form-control" path="product_id" />
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label class="form-label">Name</label>
                                            <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}" path="name" />
                                            ${errorName}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label class="form-label">Category</label>
                                            <form:select class="form-control ${not empty errorCategory ? 'is-invalid' : ''}" path="category">
                                                <form:option value="" label="-- Select Category --" />
                                                <form:options items="${categories}" itemValue="category_id" itemLabel="name" />
                                            </form:select>
                                            ${errorCategory}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label class="form-label">Price</label>
                                            <form:input type="number" class="form-control ${not empty errorPrice ? 'is-invalid' : ''}" path="price" />
                                            ${errorPrice}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label class="form-label">Short description</label>
                                            <form:input
                                                type="text"
                                                class="form-control ${not empty errorShortDesc ? 'is-invalid' : ''}"
                                                path="shortDesc"
                                            />
                                            ${errorShortDesc}
                                        </div>
                                        <div class="mb-3 col-12">
                                            <label class="form-label">Detail description</label>
                                            <form:textarea
                                                type="text"
                                                class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}"
                                                path="detailDesc"
                                            />
                                            ${errorDetailDesc}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label class="form-label">Stock</label>
                                            <form:input type="number" class="form-control ${not empty errorStock ? 'is-invalid' : ''}" path="stock" />
                                            ${errorStock}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="productFile" class="form-label">Image</label>
                                            <input class="form-control" type="file" id="productFile" accept=".png, .jpg, .jpeg" name="productFile" />
                                        </div>
                                        <div class="col-12 mb-3">
                                            <img style="max-height: 250px; display: none" alt="product preview" id="productPreview" />
                                        </div>
                                        <div class="col-12 mb-5">
                                            <button type="submit" class="btn btn-warning">Update</button>
                                            <a href="/shop/product" class="btn btn-success">Back</a>
                                        </div>
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
        <script src="/js/scripts.js"></script>
    </body>
</html>
