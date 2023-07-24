package com.example.thith.Service;

import com.example.thith.Model.Degree;

import java.util.List;

public interface DegreeService {
    List<Degree> findAll();
    Degree findById(int id);
}
