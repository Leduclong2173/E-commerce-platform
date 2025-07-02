<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    /* Sử dụng flexbox để đẩy footer xuống dưới */
    html,
    body {
        height: 100%;
        margin: 0;
    }
    body {
        display: flex;
        flex-direction: column;
    }
    /* Nội dung chính chiếm không gian còn lại */
    .content {
        flex: 1 0 auto;
    }
    /* Footer luôn ở dưới cùng */
    .footer,
    .copyright {
        flex-shrink: 0;
    }
</style>

<!-- Giả sử đây là phần nội dung chính của trang -->
<div class="content">
    <!-- Nội dung trang của bạn ở đây -->
</div>

<!-- Footer Start -->
<div class="container-fluid bg-dark text-white-50 footer pt-5">
    <div class="container py-5">
        <div class="pb-4 mb-4" style="border-bottom: 1px ledge rgba(226, 175, 24, 0.5)">
            <div class="row g-4">
                <div class="col-lg-3">
                    <a>
                        <h1 class="text-primary mb-0">Dl Ecommerce</h1>
                        <p class="text-secondary mb-0">@Leduclong</p>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer End -->

<!-- Copyright Start -->
<div class="container-fluid copyright bg-dark py-4">
    <div class="container">
        <div class="row">
            <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                <span class="text-light">
                    <a><i class="fas fa-copyright text-light me-2"></i>Le Duc Long - Graduation project</a>
                </span>
            </div>
            <div class="col-md-6 my-auto text-center text-md-end text-white">Designed By <a>Long</a></div>
        </div>
    </div>
</div>
<!-- Copyright End -->
