package com.example.thith.Service;

import com.example.thith.Model.Degree;
import com.example.thith.Model.EGender;
import com.example.thith.Model.Teacher;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherServiceMySQL implements ITeacherService {
    private static final String FIND_ALL_TEACHERS = "SELECT teacher.*, degree.id as id_cate, degree.name as name_cate \n" +
            "FROM teacher join degree on teacher.degree = degree.id;";
    private static final String FIND_BY_ID = "SELECT * FROM teacher\n" +
            "join degree on teacher.degree = degree.id where teacher.id  = ?;";
    private String jdbcURL = "jdbc:mysql://localhost:3306/teacher_management?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "voquangminh";

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TEACHERS);

            System.out.println("findAll:" + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");

                String name = rs.getString("name");

                java.sql.Date sqlDate = rs.getDate("DoB");
                LocalDate localDate = sqlDate.toLocalDate();

                String description = rs.getString("hobbie");

                String gender = rs.getString("gender");
                EGender eGender = EGender.find(gender);

                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");

                Teacher teacher = new Teacher(id, name, localDate, description, eGender);

                Degree degree = new Degree(idCate, nameCate);
                teacher.setDegree(degree);

                teachers.add(teacher);
            }
            connection.close();
        } catch (SQLException exception) {
            printSQLException(exception);
        }
        return teachers;
    }

    @Override
    public void save(Teacher teacher)  {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `teacher` (`name`, `DoB`, `hobbie`, `gender`, `degree`) VALUES (?, ?, ?, ?, ?);\n");

            preparedStatement.setString(1, teacher.getName());
            LocalDate localDate = teacher.getDob();
            java.sql.Date createAt = java.sql.Date.valueOf(localDate);
            preparedStatement.setDate(2, createAt);
            preparedStatement.setString(3, teacher.getHobbie());
            preparedStatement.setString(4, teacher.getGender().getName());
            preparedStatement.setInt(5, teacher.getDegree().getId());

            System.out.println("Save: " + preparedStatement);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException exception) {
            printSQLException(exception);
        }
    }

    @Override
    public Teacher findById(int id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);

            preparedStatement.setLong(1, id);

            System.out.println("FindByID: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idTeacher = rs.getInt("id");
                String name = rs.getString("name");
                java.sql.Date sqlDate = rs.getDate("DoB");
                LocalDate localDate = sqlDate.toLocalDate();
                String description = rs.getString("hobbie");

                String gender = rs.getString("gender");
                EGender eGender = EGender.find(gender);

                Teacher teacher = new Teacher(idTeacher, name, localDate, description, eGender);
                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");
                Degree degree = new Degree(idCate, nameCate);
                teacher.setDegree(degree);
                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int id, Teacher teacher) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `teacher` SET `name` = ?, `DoB` = ?, `hobbie` = ?, `gender` = ?, `degree` = ? WHERE (`id` = ?);\n");
            preparedStatement.setString(1, teacher.getName());

            LocalDate localDate = teacher.getDob();
            java.sql.Date createAt = java.sql.Date.valueOf(localDate);
            preparedStatement.setDate(2, createAt);

            preparedStatement.setString(3, teacher.getHobbie());
            preparedStatement.setString(4, teacher.getGender().getName());
            preparedStatement.setInt(5,teacher.getDegree().getId());
            preparedStatement.setInt(6, id);

            System.out.println("update: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (SQLException exception) {
            printSQLException(exception);
        }
    }

    @Override
    public void remove(int id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `teacher` WHERE (`id` = ?);");
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
