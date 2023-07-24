<%-- Created by IntelliJ IDEA. User: Admin Date: 8/5/2023 Time: 11:46 AM To change this template use File | Settings |
    File Templates. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <title>Title</title>
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.2/css/bootstrap.min.css"
        integrity="sha512-rt/SrQ4UNIaGfDyEXZtNcyWvQeOq0QLygHluFQcSjaGB04IxWhal71tKuzP6K8eYXYB6vJV4pHkXcmFGGQ1/0w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.2/js/bootstrap.min.js"
          integrity="sha512-7rusk8kGPFynZWu26OKbTeI+QPoYchtxsmPeBqkHIEXJxeun4yJ4ISYe7C6sz9wdxeE1Gk3VxsIWgCZTc+vX3g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
        integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.5/sweetalert2.all.min.js"
          integrity="sha512-2JsZvEefv9GpLmJNnSW3w/hYlXEdvCCfDc+Rv7ExMFHV9JNlJ2jaM+uVVlCI1MAQMkUG8K83LhsHYx1Fr2+MuA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>

<body>
<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-6">
      <form method="post">
        <div class="row mb-2">
          <label class="col-4">Tên</label>
          <input name="name" class="col-8 form-control"
                 value="${requestScope.teacher.getName()}"/>
        </div>

        <div class="row mb-2">
          <label class="col-4">Sở Thích</label>
          <input name="hobbie" class="col-8 form-control"
                 value="${requestScope.student.getHobbie()}"/>
        </div>

        <div class="row mb-2">
          <label class="col-4">Giới Tính: </label>
          <select name="gender" class="col-8 form-control">
            <c:forEach items="${requestScope.genders}" var="g">
              <option value="${g.getId()}">${g.getName()}</option>
            </c:forEach>
          </select>
        </div>

        <div class="row mb-2">
          <label class="col-4">Ngày Sinh:</label>
          <input name="dob" type="date" class="col-8 form-control"
                 value="${requestScope.teacher.getDob()}"/>
        </div>

        <div class="row mb-2">
          <label class="col-4" for="">Bằng Cấp: </label>
          <select name="degree" class="col-8 form-control">
            <c:forEach items="${requestScope.degrees}" var="ct">
              <option value="${ct.getId()}">${ct.getName()}</option>
            </c:forEach>
          </select>
        </div>

        <div class="row">
          <input type="submit" class="mybutton offset-4 btn btn-primary" />
          <a href="/students">
            <button type="button" class="mybutton btn btn-dark">Back</button>
          </a>
        </div>
      </form>
    </div>
  </div>
</div>
</body>

</html>
