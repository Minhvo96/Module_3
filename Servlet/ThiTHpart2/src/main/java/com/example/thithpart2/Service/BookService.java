package com.example.thithpart2.Service;

import com.example.thithpart2.DAO.BookDAO;
import com.example.thithpart2.Model.Book;
import com.example.thithpart2.Service.DTO.PageableRequest;
import com.example.thithpart2.Util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static List<Book> books;

    private static Long currentId = 0L;

    private static final BookService bookService;

    private BookDAO bookDAO = new BookDAO();

    static {
        books = new ArrayList<>();
        bookService = new BookService();
    }

    public List<Book> getBooks(PageableRequest request) {
        return bookDAO.findAll(request);
    }

    public Book findById(Long id) {
        return bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, "Book")));
    }

    public void create(Book book) {
        bookDAO.insertBook(book);
    }

    public static BookService getBookService() {
        return bookService;
    }

    private BookService() {
    }

    public void edit(Book book) {
        bookDAO.updateBook(book);
    }

    public boolean existById(Long id) {
        return bookDAO.findById(id).orElse(null) != null;
    }

    public void delete(Long bookId) {
        bookDAO.deleteById(bookId);
    }
}
