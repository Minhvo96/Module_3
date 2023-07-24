<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.2/css/bootstrap.min.css"
          integrity="sha512-rt/SrQ4UNIaGfDyEXZtNcyWvQeOq0QLygHluFQcSjaGB04IxWhal71tKuzP6K8eYXYB6vJV4pHkXcmFGGQ1/0w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.2/js/bootstrap.min.js"
            integrity="sha512-7rusk8kGPFynZWu26OKbTeI+QPoYchtxsmPeBqkHIEXJxeun4yJ4ISYe7C6sz9wdxeE1Gk3VxsIWgCZTc+vX3g=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.5/sweetalert2.all.min.js"
            integrity="sha512-2JsZvEefv9GpLmJNnSW3w/hYlXEdvCCfDc+Rv7ExMFHV9JNlJ2jaM+uVVlCI1MAQMkUG8K83LhsHYx1Fr2+MuA=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" type="text/css" href="admin/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <!-- or -->
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<div class="container">
    <div style="display: flex; justify-content: space-between">
        <div>
            <a href="/teachers?action=create">
                <button class="btn btn-primary">Thêm mới</button>
            </a>
        </div>
        <div class="search_bar">
            <form action="/students?action=search" method="POST">
                <select name="category-type">
                    <option value="-1">Giới Tính</option>
                    <c:forEach items="${categories}" var="ct">
                        <option ${pageable.getCategoryType() == ct.getId() ? 'selected' : ''}  value="${ct.getId()}">${ct.getName()}</option>
                    </c:forEach>
                </select>
                <select name="category-type">
                    <option value="-1">Bằng Cấp</option>
                    <c:forEach items="${categories}" var="ct">
                        <option ${pageable.getCategoryType() == ct.getId() ? 'selected' : ''}  value="${ct.getId()}">${ct.getName()}</option>
                    </c:forEach>
                </select>
                <button class="btn btn-primary" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
            </form>
        </div>
    </div>

    <form action="/teachers?action=delete" method="post" id="frmDelete">
        <input id="txtDeleteId" type="hidden" name="id">
    </form>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Tên</th>
            <th>Sở Thích</th>
            <th>Ngày Sinh</th>
            <th>Giới tính</th>
            <th>Bằng Cấp</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.teachers}" var="t">
            <tr>
                <td>${t.getName()}</td>
                <td>${t.getDob()}</td>
                <td>${t.getHobbie()}</td>
                <td>${t.getGender()}</td>
                <td>${t.getDegree().getName()}</td>
                <td>
                    <a href="/teachers?action=edit&id=${s.getId()}"><button class="btn btn-primary btn-sm edit" type="button" title="Sửa"
                                                                                   id="show-emp"><i
                            class="fas fa-edit"></i>
                    </button></a>
                    <a href="javascript:;" onclick="handleDelete(${t.getId()}, '${t.getName()}')"><button class="btn btn-primary btn-sm"><i
                            class="fa-solid fa-trash"></i></button></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
