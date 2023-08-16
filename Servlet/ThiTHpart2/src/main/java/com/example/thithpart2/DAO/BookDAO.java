package com.example.thithpart2.DAO;

import com.example.thithpart2.Model.Author;
import com.example.thithpart2.Model.Book;
import com.example.thithpart2.Model.Category;
import com.example.thithpart2.Model.EType;
import com.example.thithpart2.Service.DTO.PageableRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO extends DatabaseConnection {

    private final String SELECT_ALL_BOOKS = "SELECT b.*, a.name as author_name, c.name as category_name \n" +
            "FROM book as b left join authors as a on b.author = a.id\n" +
            "join categories as c on b.category = c.id \n" +
            "WHERE b.`title` like '%s' OR b.`description` LIKE '%s' OR a.`name` LIKE '%s' OR c.`name` LIKE '%s' OR b.`type` LIKE '%s';";

    //    private final String SELECT_TOTAL_RECORDS = "SELECT COUNT(1) as cnt  FROM `teacher` t LEFT JOIN " +
//            "`degree` d on t.degree = d.id  WHERE t.`name` like '%s' OR t.`hobbie` LIKE '%s'";
    private final String UPDATE_BOOK = "UPDATE `book` SET `title` = ?, `publish_date` = ?, `description` = ?, `price` = ?, `author` = ?, `category` = ?, `type` = ?  WHERE (`id` = ?);";

    private final String INSERT_BOOK = "INSERT INTO `book` (`title`, `publish_date`, `description`, `price`, `author`, `category`, `type`) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_BY_ID = "SELECT b.*, a.name as author_name, c.name as category_name FROM " +
            "`book` b LEFT JOIN `authors` a on b.author = a.id join `categories` c on b.category = c.id WHERE b.`id` = ?";

    private final String DELETE_BY_ID = "DELETE FROM `book` WHERE (`id` = ?)";


    public List<Book> findAll(PageableRequest request) {
        List<Book> books = new ArrayList<>();
        String search = request.getSearch();
//        if(request.getSortField() == null){
//            request.setSortField("id");
//        }
//        if(request.getSortType() == null){
//            request.setSortType(ESortType.DESC);
//        }
        if(search == null){
            search = "%%";
        }else {
            search = "%" + search + "%";
        }
//        int offset = (request.getPage() - 1) * request.getLimit();
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(String.format(SELECT_ALL_BOOKS, search, search, search, search, search))) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            //

            while (rs.next()) {

                books.add(getBookByResultSet(rs));
            }
//            PreparedStatement statementTotalRecords = connection
//                    .prepareStatement(String.format(SELECT_TOTAL_RECORDS, search, search));
//            ResultSet resultSetOfTotalRecords = statementTotalRecords.executeQuery();
//            if(resultSetOfTotalRecords.next()){
//                int totalPage =
//                        (int) Math.ceil(resultSetOfTotalRecords.getDouble("cnt")/request.getLimit());
//                request.setTotalPage(totalPage);
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public void insertBook(Book book){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_BOOK)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setDate(2,book.getPublishDate());
            preparedStatement.setString(3,book.getDescription());
            preparedStatement.setFloat(4, book.getPrice());
            preparedStatement.setLong(5, book.getAuthor().getId());
            preparedStatement.setLong(6, book.getCategory().getId());
            preparedStatement.setString(7,book.getType().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(Book book) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_TEACHER)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE_BOOK)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setDate(2,book.getPublishDate());
            preparedStatement.setString(3,book.getDescription());
            preparedStatement.setFloat(4, book.getPrice());
            preparedStatement.setLong(5, book.getAuthor().getId());
            preparedStatement.setLong(6, book.getCategory().getId());
            preparedStatement.setString(7,book.getType().toString());
            preparedStatement.setLong(8,book.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Book> findById(Long id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(getBookByResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void deleteById(Long id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book getBookByResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        String publishDate = rs.getString("publish_date");
        String description = rs.getString("description");
        Float price = rs.getFloat("price");

        long authorId = rs.getLong("author");
        String authorName = rs.getString("author_name");
        Author author = new Author((int) authorId, authorName);

        long categoryId = rs.getLong("category");
        String categoryName = rs.getString("category_name");
        Category category = new Category((int) categoryId, categoryName);

        String type = rs.getString("type");

        return new Book(id, title, Date.valueOf(publishDate),description, price, author, category, EType.valueOf(type));
    }









}
