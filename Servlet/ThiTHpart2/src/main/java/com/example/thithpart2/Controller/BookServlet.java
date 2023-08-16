package com.example.thithpart2.Controller;

import com.example.thithpart2.Model.Author;
import com.example.thithpart2.Model.Book;
import com.example.thithpart2.Model.EType;
import com.example.thithpart2.Service.AuthorService;
import com.example.thithpart2.Service.BookService;
import com.example.thithpart2.Service.CategoryService;
import com.example.thithpart2.Util.AppConstant;
import com.example.thithpart2.Util.AppUtil;
import com.example.thithpart2.Util.RunnableCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.thithpart2.Service.DTO.PageableRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = "/books", name = "bookServlet")
public class BookServlet extends HttpServlet {
    private final String PAGE = "books"; // đặt hằng số

    private Map<String, RunnableCustom> validators;

    private final Map<String, String> errors = new HashMap<>(); // tạo map để validators add lỗi vào map này

    @Override
    public void init() {
        validators = new HashMap<>();
        // tạo validator với name field là phone, và nó validate theo Regex Pattern
        // tạo tất các validator cho all fields.
        // mình có thế xài cái thằng khác
//        validators.put("phone", new RunnableWithRegex("[0-9]{10}", "phone", errors));
        //validators.put("dob", new RunnableWithRegex("[0-9]{10}", "dob", errors));
        //định nghĩa tất cả các fields
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(AppConstant.ACTION);

        if(Objects.equals(action, AppConstant.EDIT)){
            showEdit(req,resp);
            return;
        }
        if (Objects.equals(action, AppConstant.CREATE)) {
            showCreate(req, resp);
            return;
        }
        if (Objects.equals(action, AppConstant.DELETE)) {
            delete(req, resp);
            return;
        }
        showList(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errors.clear(); // clear lỗi cũ
        String action = req.getParameter(AppConstant.ACTION); // lấy action
        if (Objects.equals(action, AppConstant.CREATE)) {
            //kiểm tra xem action = create thi call create
            create(req, resp);
            return;
        }
        if (Objects.equals(action, AppConstant.EDIT)) {
            //kiểm tra xem action = create thi call edit
            edit(req, resp);
            return;
        }
        showList(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Book book = getValidBook(req,resp); // lấy ra user và + xử lý cho việc validation của các field trong class User.
        if(errors.size() == 0){ //không xảy lỗi (errors size == 0) thì mình mới tạo user.
            BookService.getBookService().create(book);
            resp.sendRedirect("/books?message=Created");
        }

    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Book book = getValidBook(req,resp); // lấy ra user và + xử lý cho việc validation của các field trong class User.
        if(errors.size() == 0){ //không xảy lỗi (errors size == 0) thì mình mới sửa user.
            BookService.getBookService().edit(book);
            resp.sendRedirect("/books?message=Edited");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //page, Integer totalOfPage, Integer limit, Integer totalPage
        PageableRequest request = new PageableRequest(
                req.getParameter("search")
//                req.getParameter("sortField"),
//                ESortType.valueOf(AppUtil.getParameterWithDefaultValue(req,"sortType", ESortType.DESC).toString()),
//                Integer.parseInt(AppUtil.getParameterWithDefaultValue(req, "page", "1").toString()),
//                Integer.parseInt(AppUtil.getParameterWithDefaultValue(req, "limit", "5").toString())
        ); //tao doi tuong pageable voi parametter search

//        req.setAttribute("pageable", request);
        req.setAttribute("books", BookService.getBookService().getBooks(request)); // gửi qua list users để jsp vẻ lên trang web
        req.setAttribute("booksJSON", new ObjectMapper().writeValueAsString(BookService.getBookService().getBooks(request)));
        req.setAttribute("message", req.getParameter("message")); // gửi qua message để toastr show thông báo
        req.setAttribute("typeJSON", new ObjectMapper().writeValueAsString(EType.values()));
        req.setAttribute("authorsJSON", new ObjectMapper().writeValueAsString(AuthorService.getAuthors()));
        req.setAttribute("categoriesJSON", new ObjectMapper().writeValueAsString(CategoryService.getCategories()));
        req.getRequestDispatcher(PAGE + AppConstant.LIST_PAGE).forward(req,resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("bookJSON", new ObjectMapper().writeValueAsString(new Book())); // gửi qua user rỗng để JS vẻ lên trang web
        req.setAttribute("typeJSON", new ObjectMapper().writeValueAsString(EType.values()));
        req.setAttribute("authorsJSON", new ObjectMapper().writeValueAsString(AuthorService.getAuthors()));
        req.setAttribute("categoriesJSON", new ObjectMapper().writeValueAsString(CategoryService.getCategories()));
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);
    }
    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;
        req.setAttribute("typeJSON", new ObjectMapper().writeValueAsString(EType.values()));
        req.setAttribute("book", BookService.getBookService().findById(id)); // gửi user để jsp check xem edit hay là create User
        req.setAttribute("bookJSON", new ObjectMapper().writeValueAsString(BookService.getBookService().findById(id))); // gửi qua user được tìm thấy bằng id để JS vẻ lên trang web
        req.setAttribute("authorsJSON", new ObjectMapper().writeValueAsString(AuthorService.getAuthors()));
        req.setAttribute("categoriesJSON", new ObjectMapper().writeValueAsString(CategoryService.getCategories()));
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);

    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;
        BookService.getBookService().delete(id);
        resp.sendRedirect(PAGE + "?message=Deleted");
    }

    private Book getValidBook(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Book book = (Book) AppUtil.getObjectWithValidation(req, Book.class,  validators); //
        if(errors.size() > 0){
            req.setAttribute("bookJSON", new ObjectMapper().writeValueAsString(book)); //hiểu dòng đơn giản là muốn gửi data qua JS thì phải xài thằng này  new ObjectMapper().writeValueAsString(user).
            req.setAttribute("authorsJSON", new ObjectMapper().writeValueAsString(AuthorService.getAuthors()));
            req.setAttribute("categoriesJSON", new ObjectMapper().writeValueAsString(CategoryService.getCategories()));
            req.setAttribute("typeJSON", new ObjectMapper().writeValueAsString(EType.values()));
            req.setAttribute("message","Something was wrong");
            req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                    .forward(req,resp);
        }
        return book;
    }

    private boolean checkIdNotFound(HttpServletRequest req, HttpServletResponse resp, Long id) throws IOException{
        if(!BookService.getBookService().existById(id)){
            resp.sendRedirect(PAGE + "?message=Id not found");
            return true;
        }
        return false;
    }
}
