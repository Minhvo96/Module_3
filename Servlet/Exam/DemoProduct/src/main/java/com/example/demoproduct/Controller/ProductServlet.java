package com.example.demoproduct.Controller;
import com.example.demoproduct.Model.Category;
import com.example.demoproduct.Model.Product;
import com.example.demoproduct.Service.CategoryServiceMySQL;
import com.example.demoproduct.Service.ICategoryService;
import com.example.demoproduct.Service.IProductService;
import com.example.demoproduct.Service.ProductServiceMySQL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private ICategoryService categoryService = new CategoryServiceMySQL();
    private IProductService productService = new ProductServiceMySQL();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //localhost:8080/customers          // show list
        //localhost:8080/customers?action=create
        //localhost:8080/customers?action=edit&id=2
        //localhost:8080/customers?action=delete&id=2
        //localhost:8080/customers?action=advavd            // show list
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req, resp);
                break;
            case "edit":
                showEdit(req, resp);
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        productService.remove(id);

        req.getSession().setAttribute("messageDelete", "Xóa thành công");
        resp.sendRedirect("/products");
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long idProduct = Long.parseLong(req.getParameter("id"));

        Product product = productService.findById(idProduct);
        req.setAttribute("product", product);

        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/edit.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList = productService.findAll();
        req.setAttribute("products", productList);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                saveProduct(req, resp);
                break;
            case "edit":
                updateProduct(req, resp);
                break;
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String createAtStr = req.getParameter("createAt");
        LocalDate createAt = LocalDate.parse(createAtStr);


        Product product = productService.findById(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCreateAt(createAt);

        int idCate = Integer.parseInt(req.getParameter("category"));
        Category category = categoryService.findById(idCate);
        product.setCategory(category);

        productService.update(id, product);
        req.getSession().setAttribute("messageEdit", "Sửa thành công");
        resp.sendRedirect("/products");            // Dùng respone để sendRedirect


    }

    private void saveProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        String priceString = req.getParameter("price");
        BigDecimal price = new BigDecimal(priceString);

        String createAtStr = req.getParameter("createAt");
        LocalDate createAt = LocalDate.parse(createAtStr);

        long id = (long)(Math.random() * 10000);
        Product product = new Product(id, name, description, price, createAt);

        int idCate = Integer.parseInt(req.getParameter("category"));
        Category category = categoryService.findById(idCate);
        product.setCategory(category);
        productService.save(product);

        req.setAttribute("message", "Thêm thành công");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/create.jsp");
        requestDispatcher.forward(req, resp);
    }
}
