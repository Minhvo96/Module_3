<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Title</title>
  <link href="../style.css" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js" integrity="sha512-3gJwYpMe3QewGELv8k/BX9vcqhryRdzRMxVfq6ngyWXwo03GFEzjsUm8Q7RZcHPHksttq7/GFoxjCVUjkjvPdw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js" integrity="sha512-VEd+nq25CkR676O+pLBnDW09R7VQX9Mdiij052gVCp5yVH3jGtH70Ho/UUv4mJDsEdTvqRCFZg0NKGiojGnUCw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.css" integrity="sha512-3pIirOrwegjM6erE5gPSwkUzO+3cTjpnV9lexlNZqvupR64iZBnOOTiiLPb9M36zpMScbmUNIcHUqKD47M719g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<h1>Create</h1>
<h3>${message}</h3>
<form method="post"
        <c:if test = "${book.id == null}"> action="/books?action=create" </c:if>
        <c:if test = "${book.id != null}"> action="/books?action=edit" </c:if>
>
  <%--        cần phải có thằng formBody thì xài được validation js--%>
  <div id="formBody" class="row">

  </div>
  <button class="btn btn-primary" type="submit">
    <c:if test = "${book.id != null}"> Edit Book </c:if>
    <c:if test = "${book.id == null}"> Create Book </c:if>
  </button>
  <a href="/books" class="btn btn-secondary" onclick="console.log(${book.toString()})">Back</a>
</form>
<script src="../base.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<%--    <% var user = (User) request.getAttribute("user"); %>--%>
<script>
  <%--const user =<%= user%>;--%>
  //lấy được userở đây mấy ae tra google để
  // lấy data user từ controller
  var book = ${booksJSON};
  const types = ${typeJSON};
  const authors = ${authorsJSON};
  const categories = ${categoriesJSON};
  const inputs = [
    {
      label: "Title",
      name: "tile",
      pattern: "^[A-Za-z ]{6,25}",
      message: "Title must have minimum is 6 charaters and maximum is 25 charaters",
      require: true,
      classDiv: 'col-6',
      value: book.title || ''
    },
    {
      label: "Description",
      name: "description",
      message: "Description must have minimum is 6 charaters and maximum is 20 charaters",
      // disable: book.description,
      pattern: "^[A-Za-z]{6,20}",
      require: true,
      value:  book.description || '',
      classDiv: 'col-6'
    },
    {
      label: "Publish Date",
      name: "date",
      type: "date",
      require: true,
      value:  book.publishDate || '',
      classDiv: 'col-6'
    },
    {
      label: "Price",
      name: "price",
      pattern: "^[1-9][0-9]*$", // Pattern for Price: Positive integer greater than 0
      message: "Price must be a number and greater than 0",
      require: true,
      value: book.price || '',
      classDiv: 'col-6'
    },
    {
      label: "Author",
      name: "role_id",
      type: "select",
      message: "Please choose Author",
      options: authors?.map(e=> {
        return {
          name: e.name,
          value: e.id
        }
      }),
      require: true,
      value: book.author?.id || '', // có nghĩa là  nếu user.role có giá trị thì sẽ lấy id của role còn không thì lấy ''
      classDiv: 'col-6'
    },
    {
      label: "Category",
      name: "position_id",
      type: "select",
      message: "Please choose Category",
      options: categories?.map(e=> {
        return {
          name: e.name,
          value: e.id
        }
      }),
      require: true,
      value: book.category?.id || '', // có nghĩa là  nếu user.role có giá trị thì sẽ lấy id của role còn không thì lấy ''
      classDiv: 'col-6'
    },
    {
      label: "Type",
      name: "type",
      type: "select",
      message: "Please choose type",
      options: types?.map(e=> {
        return {
          name: e,
          value: e
        }
      }),
      require: true,
      value: book.type?.id || '',
      classDiv: 'col-6'
    }
  ];
  // phải có những dòng dưới này

  const formBody = document.getElementById('formBody'); // DOM formBody theo id

  // loop qua inputs
  inputs.forEach((props, index) => {
    // vẽ từng ô input
    formBody.innerHTML += formInput(props, index);
  })
</script>

</body>
</html>

