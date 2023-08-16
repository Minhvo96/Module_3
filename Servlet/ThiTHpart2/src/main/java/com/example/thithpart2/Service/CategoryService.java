package com.example.thithpart2.Service;



import com.example.thithpart2.DAO.CategoryDAO;
import com.example.thithpart2.Model.Category;

import java.util.List;

public class CategoryService {
    public static List<Category> getCategories(){
        return new CategoryDAO().getCategories();
    }
}
