package com.example.thith.Servlet;
import com.example.thith.Model.Degree;
import com.example.thith.Model.EGender;
import com.example.thith.Model.Teacher;
import com.example.thith.Service.DegreeService;
import com.example.thith.Service.DegreeServiceMySQL;
import com.example.thith.Service.ITeacherService;
import com.example.thith.Service.TeacherServiceMySQL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "TeacherServlet", urlPatterns = "/teachers")
public class TeacherServlet extends HttpServlet {

    private DegreeService degreeService = new DegreeServiceMySQL();
    private ITeacherService teacherService = new TeacherServiceMySQL();

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
                    deleteTeacher(req, resp);
                    break;
                default:
                    showList(req, resp);
            }
        }

        private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            int id = Integer.parseInt((req.getParameter("id")));
            teacherService.remove(id);

            req.getSession().setAttribute("messageDelete", "Xóa thành công");
            resp.sendRedirect("/teachers");
        }

        private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int idTeacher = Integer.parseInt((req.getParameter("id")));

            Teacher teacher = teacherService.findById(idTeacher);
            req.setAttribute("teacher", teacher);

            List<Degree> degrees = degreeService.findAll();
            req.setAttribute("degrees", degrees);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/display/edit.jsp");
            requestDispatcher.forward(req, resp);

        }

        private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Teacher> teachersList = teacherService.findAll();
            req.setAttribute("teachers", teachersList);


            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/display/list.jsp");
            requestDispatcher.forward(req, resp);
        }

        private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Degree> degrees = degreeService.findAll();
            req.setAttribute("degrees", degrees);

            EGender[] genders = EGender.values();
            req.setAttribute("genders", genders);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/display/create.jsp");
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
                    saveTeacher(req, resp);
                    break;
                case "edit":
                    updateTeacher(req, resp);
                    break;
            }
        }

        private void updateTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//            int id, String name, LocalDate dob, String hobbie, EGender gender, Degree degree

            int id = Integer.parseInt((req.getParameter("id")));
            String name = req.getParameter("name");

            String createAtStr = req.getParameter("dob");
            LocalDate createAt = LocalDate.parse(createAtStr);

            String hobbie= req.getParameter("hobbie");

            int idGender = Integer.parseInt(req.getParameter("gender"));
            EGender eGender = EGender.findById(idGender);

            Teacher teacher = teacherService.findById(id);
            teacher.setName(name);
            teacher.setDob(createAt);
            teacher.setHobbie(hobbie);
            teacher.setGender(eGender);

            int idCate = Integer.parseInt(req.getParameter("degree"));
            Degree degree = degreeService.findById(idCate);
            teacher.setDegree(degree);

            teacherService.update(id, teacher);
            req.getSession().setAttribute("messageEdit", "Sửa thành công");
            resp.sendRedirect("/teachers");            // Dùng respone để sendRedirect


        }

        private void saveTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            int id = Integer.parseInt((req.getParameter("id")));
            String name = req.getParameter("name");

            String createAtStr = req.getParameter("dob");
            LocalDate createAt = LocalDate.parse(createAtStr);

            String hobbie= req.getParameter("hobbie");

            int idGender = Integer.parseInt(req.getParameter("gender"));
            EGender eGender = EGender.findById(idGender);

            Teacher teacher = new Teacher(id, name, createAt, hobbie, eGender);

            int idCate = Integer.parseInt(req.getParameter("degree"));
            Degree degree = degreeService.findById(idCate);
            teacher.setDegree(degree);
            teacherService.save(teacher);

            req.setAttribute("message", "Thêm thành công");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/display/create.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
