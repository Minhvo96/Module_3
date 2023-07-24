package com.example.thith.Service;

import com.example.thith.Model.Degree;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DegreeServiceMySQL implements DegreeService{
    private static final String SELECT_ALL_DEGREE_TYPES = "SELECT * FROM degree;";
    private static final String FIND_BY_ID = "SELECT * FROM degree where id = ?;";

    private String jdbcURL = "jdbc:mysql://localhost:3306/teacher_management?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "voquangminh";

    @Override
    public List<Degree> findAll() {
        List<Degree> degrees = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEGREE_TYPES);

            System.out.println("findAll: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCate = rs.getInt("id");
                String name = rs.getString("name");
                Degree degree= new Degree(idCate, name);

                degrees.add(degree);
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return degrees;
    }

    @Override
    public Degree findById(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);

            System.out.println("findById: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCate = rs.getInt("id");
                String name = rs.getString("name");
                Degree degree = new Degree(idCate, name);
                return degree;
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);

        }
        return null;
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
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
