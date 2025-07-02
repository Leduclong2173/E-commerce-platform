<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Information</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </head>

    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
            <jsp:include page="../layout/sidebar.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Information</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/shop">Dashboard</a></li>
                            <li class="breadcrumb-item"><a>Information</a></li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto">
                                    <h3>Information</h3>
                                    <hr />
                                    <form:form
                                        method="post"
                                        action="/shop/inforshop/update"
                                        class="row"
                                        enctype="multipart/form-data"
                                        modelAttribute="inforShop"
                                    >
                                        <c:set var="errorName">
                                            <form:errors path="name" cssClass="invalid-feedback" />
                                        </c:set>
                                        <div class="mb-3" style="display: none">
                                            <label class="form-label">Id:</label>
                                            <form:input type="text" class="form-control" path="shop_id" />
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="InputName" class="form-label">Name</label>
                                            <form:input
                                                type="text"
                                                class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                id="InputName"
                                                path="name"
                                            />
                                            ${errorName}
                                        </div>
                                        <div class="mb-3 col-12">
                                            <label for="InputDescription" class="form-label">Description</label>
                                            <form:input type="text" class="form-control" id="InputDescription" path="description" />
                                        </div>
                                        <div class="col-12 mb-5">
                                            <button type="submit" class="btn btn-warning">Update</button>
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
    </body>
</html>
