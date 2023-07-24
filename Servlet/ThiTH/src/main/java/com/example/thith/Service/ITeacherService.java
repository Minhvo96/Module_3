package com.example.thith.Service;
import com.example.thith.Model.Teacher;
import java.util.List;

public interface ITeacherService {
    List<Teacher> findAll();

    void save(Teacher teacher);

    Teacher findById(int id);

    void update(int id, Teacher teacher);

    void remove(int id);


}
