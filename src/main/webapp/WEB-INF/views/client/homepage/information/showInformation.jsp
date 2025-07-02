<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Information</title>
        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet" />

        <!-- Icon Font Stylesheet -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet" />

        <!-- Libraries Stylesheet -->
        <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet" />
        <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="/client/css/bootstrap.min.css" rel="stylesheet" />

        <!-- Template Stylesheet -->
        <link href="/client/css/style.css" rel="stylesheet" />

        <!-- Custom CSS for uniform image size -->
        <style>
            .fruite-img img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                display: block;
            }
        </style>

        <meta name="_csrf" content="${_csrf.token}" />
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}" />

        <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css" rel="stylesheet" />
    </head>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            const orgImage = "${inforUser.avatar}";

            if (orgImage && orgImage !== "") {
                const urlImage = "/images/avatar/" + orgImage;
                $("#avatarPreview").attr("src", urlImage);
                $("#avatarPreview").css({ display: "block" });
            }

            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ display: "block" });
            });
        });
    </script>

    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto mt-5">
                                    <h3>Information</h3>
                                    <hr />
                                    <form:form
                                        method="post"
                                        action="/infor/update"
                                        class="row"
                                        enctype="multipart/form-data"
                                        modelAttribute="inforUser"
                                    >
                                        <c:set var="errorUsername">
                                            <form:errors path="username" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorPassword">
                                            <form:errors path="password" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorName">
                                            <form:errors path="name" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorEmail">
                                            <form:errors path="email" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorPhone">
                                            <form:errors path="phone" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorRole">
                                            <form:errors path="role.name" cssClass="invalid-feedback" />
                                        </c:set>
                                        <div class="mb-3" style="display: none">
                                            <label class="form-label">Id:</label>
                                            <form:input type="text" class="form-control" path="user_id" />
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="InputUsername" class="form-label">Username</label>
                                            <form:input
                                                type="text"
                                                class="form-control ${not empty errorUsername ? 'is-invalid' : ''}"
                                                id="InputUsername"
                                                path="username"
                                            />
                                            ${errorUsername}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6" style="display: none">
                                            <label for="InputPassword" class="form-label">Password</label>
                                            <form:input
                                                type="password"
                                                class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                                id="InputPassword"
                                                path="password"
                                            />
                                            ${errorPassword}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="InputName" class="form-label">Name</label>
                                            <form:input
                                                type="text"
                                                class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                id="InputEmail"
                                                path="name"
                                            />
                                            ${errorName}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6" style="display: none">
                                            <label class="form-label">Role</label>
                                            <form:select class="form-select ${not empty errorRole ? 'is-invalid' : ''}" path="role.name">
                                                <form:option value="">-- Select Role --</form:option>
                                                <form:option value="ADMIN">ADMIN</form:option>
                                                <form:option value="USER">USER</form:option>
                                                <form:option value="SHOP">SHOP</form:option>
                                            </form:select>
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="InputEmail" class="form-label">Email</label>
                                            <form:input
                                                type="email"
                                                class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                                                id="InputEmail"
                                                path="email"
                                            />
                                            ${errorEmail}
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="InputPhone" class="form-label">Phone</label>
                                            <form:input
                                                type="text"
                                                class="form-control ${not empty errorPhone ? 'is-invalid' : ''}"
                                                id="InputPhone"
                                                path="phone"
                                            />
                                            ${errorPhone}
                                        </div>
                                        <div class="mb-3 col-12">
                                            <label for="InputAddress" class="form-label">Address</label>
                                            <form:input type="text" class="form-control" id="InputAddress" path="address" />
                                        </div>
                                        <div class="mb-3 col-12 col-md-6">
                                            <label for="avatarFile" class="form-label">Avatar</label>
                                            <input class="form-control" type="file" id="avatarFile" accept=".png, .jpg, .jpeg" name="avatarFile" />
                                        </div>
                                        <div class="col-12 mb-3">
                                            <img style="max-height: 250px; display: none" alt="avatar preview" id="avatarPreview" />
                                        </div>
                                        <div class="col-12 mb-5">
                                            <button type="submit" class="btn btn-warning">Update</button>
                                            <a href="/" class="btn btn-success">Back</a>
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
        <!-- JavaScript Libraries -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/client/lib/easing/easing.min.js"></script>
        <script src="/client/lib/waypoints/waypoints.min.js"></script>
        <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
        <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="/client/js/main.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>

        <script src="/js/scripts.js"></script>
    </body>
</html>
