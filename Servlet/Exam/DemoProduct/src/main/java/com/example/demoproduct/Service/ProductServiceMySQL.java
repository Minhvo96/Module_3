package com.example.demoproduct.Service;

import com.example.demoproduct.Model.Category;
import com.example.demoproduct.Model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceMySQL implements IProductService {

    private static final String FIND_ALL_PRODUCTS = "SELECT p.*, ct.id as id_cate, ct.name as name_cate FROM product as p JOIN category as ct ON p.id_category = ct.id;";
    private static final String FIND_BY_ID = "SELECT p.*, ct.id as id_cate, ct.name as name_cate FROM product as p JOIN category as ct ON p.id_category = ct.id; where p.id  = ?;" ;
    private String jdbcURL = "jdbc:mysql://localhost:3306/product?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "voquangminh";

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS);

            System.out.println("findAll:" + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                java.sql.Date sqlDate = rs.getDate("createAt");
                LocalDate localDate = sqlDate.toLocalDate();

                Product product = new Product(id, name, description, price, localDate);
                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");

                Category category = new Category(idCate, nameCate);
                product.setCategory(category);

                products.add(product);
            }
            connection.close();
        } catch (SQLException exception) {
            printSQLException(exception);
        }
        return products;
    }

    @Override
    public void save(Product product) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `product` (`name`, `description`, `price`, `createAt`, `id_category`) VALUES (?, ?, ?, ?, ?);");

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            LocalDate localDate = product.getCreateAt();
            java.sql.Date createAt = java.sql.Date.valueOf(localDate);
            preparedStatement.setDate(4, createAt);
            preparedStatement.setInt(5, product.getCategory().getId());

            System.out.println("Save: " + preparedStatement);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException exception) {
            printSQLException(exception);
        }
    }

    @Override
    public Product findById(long id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);

            preparedStatement.setLong(1, id);

            System.out.println("FindByID: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long idProduct = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                java.sql.Date sqlDate = rs.getDate("createAt");
                LocalDate localDate = sqlDate.toLocalDate();

                Product product = new Product(id, name, description, price, localDate);
                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");

                Category category = new Category(idCate, nameCate);
                product.setCategory(category);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Product product) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `product` SET `name` = ?, `description` = ?, `price` = ?, `createAt` = ?, `id_category` = ? WHERE (`id` = ?);");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            LocalDate localDate = product.getCreateAt();
            java.sql.Date createAt = java.sql.Date.valueOf(localDate);
            preparedStatement.setDate(4, createAt);

            preparedStatement.setInt(5,product.getCategory().getId());
            preparedStatement.setLong(6, id);

            System.out.println("update: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (SQLException exception) {
            printSQLException(exception);
        }
    }

    @Override
    public void remove(long id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `product` WHERE (`id` = ?);");
            preparedStatement.setLong(1,id);

            System.out.println("Remove " + preparedStatement);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException exception){
            printSQLException(exception);
        }
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


}
