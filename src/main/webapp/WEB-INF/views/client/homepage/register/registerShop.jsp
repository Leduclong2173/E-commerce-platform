<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Register</title>
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
    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Register Shop</h1>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto">
                                    <hr />
                                    <form:form
                                        method="post"
                                        action="/shop/register"
                                        modelAttribute="newShop"
                                        class="row"
                                        enctype="multipart/form-data"
                                    >
                                        <c:set var="errorName">
                                            <form:errors path="name" cssClass="invalid-feedback" />
                                        </c:set>
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
                                            <button type="submit" class="btn btn-primary">Create</button>
                                            <a href="/" class="btn btn-success">Back to Homepage</a>
                                        </div>
                                        <h3>Điều khoản dịch vụ</h3>
                                        <p>
                                            Để vận hành và phát triển một nền tảng thương mại điện tử chuyên nghiệp, hiện đại và mang lại giá trị lâu
                                            dài cho cả người mua lẫn người bán, hệ thống của chúng tôi áp dụng mức phí hoa hồng 20% trên doanh thu của
                                            mỗi đơn hàng thành công. Khoản phí này được sử dụng để duy trì hạ tầng công nghệ, đảm bảo bảo mật, hỗ trợ
                                            marketing, chăm sóc khách hàng và vận hành hệ thống thanh toán an toàn, tiện lợi. Chúng tôi cam kết mang
                                            đến cho các đối tác bán hàng một môi trường kinh doanh hiệu quả, giúp mở rộng thị trường, gia tăng doanh
                                            số và xây dựng thương hiệu bền vững trong kỷ nguyên số.
                                        </p>
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
    </body>
</html>
