```jsp <%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Revenue Report</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .revenue-card {
                max-width: 800px;
                margin: 0 auto;
                padding: 30px;
                text-align: center;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .revenue-card h3 {
                color: #343a40;
                font-size: 2rem;
                margin-bottom: 30px;
            }
            .revenue-item {
                margin-bottom: 20px;
                font-size: 1.5rem;
            }
            .revenue-item label {
                font-weight: bold;
                color: #495057;
                margin-right: 10px;
            }
            .revenue-item span {
                color: #28a745;
                font-weight: bold;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="layout/header.jsp" />
        <div id="layoutSidenav">
            <jsp:include page="layout/sidebar.jsp" />
            <div id="layoutSidenav_content">
                <c:if test="${not empty message}">
                    <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                        <c:out value="${message}" />
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Revenue Report</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/shop">Dashboard</a></li>
                            <li class="breadcrumb-item active">Revenue</li>
                        </ol>
                        <div class="card revenue-card">
                            <div class="card-body">
                                <h3>Revenue for ${shop.name}</h3>
                                <div class="revenue-item">
                                    <label>Total Revenue:</label>
                                    <span><fmt:formatNumber type="number" value="${totalRevenue}" /> đ</span>
                                </div>
                                <div class="revenue-item">
                                    <label>Net Revenue (80%):</label>
                                    <span><fmt:formatNumber type="number" value="${netRevenue}" /> đ</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="layout/footer.jsp" />
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/js/scripts.js"></script>
    </body>
</html>
