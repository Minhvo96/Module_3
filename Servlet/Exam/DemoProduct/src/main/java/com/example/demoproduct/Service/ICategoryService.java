package com.example.demoproduct.Service;

import com.example.demoproduct.Model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(int id);
}
