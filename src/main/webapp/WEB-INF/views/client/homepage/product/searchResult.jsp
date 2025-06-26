<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Kết quả tìm kiếm</title>
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
        <meta name="_csrf_header" content="${_csrf.headerName}" />

        <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css" rel="stylesheet" />
    </head>
    <body>
        <!-- Spinner Start -->
        <div id="spinner"
             class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-grow text-primary" role="status"></div>
        </div>
        <!-- Spinner End -->
        <jsp:include page="../layout/header.jsp" />

        <div class="container-fluid fruite" style="margin-top: 100px;">
            <div class="container py-4">
                <div class="tab-class text-center">
                    <div class="row g-4">
                        <div class="text-start">
                            <h1 style="text-align: center">Kết quả tìm kiếm cho: ${param.keyword}</h1>
                        </div>
                    </div>
                    <div class="tab-content mt-3">
                        <div id="tab-1" class="tab-pane fade show p-0 active">
                            <div class="row g-4">
                                <div class="col-lg-12">
                                    <c:choose>
                                        <c:when test="${not empty users}">
                                            <c:forEach var="user" items="${users}">
                                                <c:if test="${not empty user.products}">
                                                    <div class="row g-4 mb-4">
                                                        <div class="col-12">
                                                            <h3 class="text-start">${user.name}</h3>
                                                            <hr />
                                                        </div>
                                                        <c:forEach var="product" items="${user.products}">
                                                            <div class="col-md-6 col-lg-4 col-xl-3">
                                                                <div class="rounded position-relative fruite-item">
                                                                    <div class="fruite-img">
                                                                        <img src="/images/product/${product.image}" class="img-fluid rounded-top" alt="${product.name}">
                                                                    </div>
                                                                    <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                        <h4 style="font-size: 15px;">
                                                                            <a href="/homepage/product/${product.product_id}">${product.name}</a>
                                                                        </h4>
                                                                        <p style="font-size: 13px;">${product.shortDesc}</p>
                                                                        <div class="d-flex flex-lg-wrap justify-content-center flex-column">
                                                                            <p style="font-size: 15px; text-align: center; width: 100%;" class="text-dark fw-bold mb-3">
                                                                                <fmt:formatNumber type="number" value="${product.price}" /> đ
                                                                            </p>
                                                                            <form action="/add-product-to-cart/${product.product_id}" method="post">
                                                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                                                <input type="hidden" name="quantity" value="1" />
                                                                                <button data-product-id="${product.product_id}" data-stock="${product.stock}" class="btnAddToCartHomepage mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                                                                    <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                                                                    Add to cart
                                                                                </button>
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-warning text-center" role="alert">
                                                Không tìm thấy sản phẩm hoặc cửa hàng nào khớp với từ khóa "${param.keyword}".
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <!-- Sản phẩm liên quan -->
                            <c:if test="${not empty relatedProducts}">
                                <div class="row g-4 mt-4">
                                    <div class="col-12">
                                        <h3 class="text-start">Sản phẩm liên quan</h3>
                                        <hr />
                                    </div>
                                    <c:forEach var="relatedProduct" items="${relatedProducts}" varStatus="status" begin="0" end="3">
                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                            <div class="rounded position-relative fruite-item">
                                                <div class="fruite-img">
                                                    <img src="/images/product/${relatedProduct.image}" class="img-fluid rounded-top" alt="${relatedProduct.name}">
                                                </div>
                                                <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                    <h4 style="font-size: 15px;">
                                                        <a href="/homepage/product/${relatedProduct.product_id}">${relatedProduct.name}</a>
                                                    </h4>
                                                    <p style="font-size: 13px;">${relatedProduct.shortDesc}</p>
                                                    <div class="d-flex flex-lg-wrap justify-content-center flex-column">
                                                        <p style="font-size: 15px; text-align: center; width: 100%;" class="text-dark fw-bold mb-3">
                                                            <fmt:formatNumber type="number" value="${relatedProduct.price}" /> đ
                                                        </p>
                                                        <form action="/add-product-to-cart/${relatedProduct.product_id}" method="post">
                                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                            <input type="hidden" name="quantity" value="1" />
                                                            <button data-product-id="${relatedProduct.product_id}" data-stock="${relatedProduct.stock}" class="btnAddToCartHomepage mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                                                <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                                                Add to cart
                                                            </button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../layout/footer.jsp" />
        <!-- Back to Top -->
        <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>

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