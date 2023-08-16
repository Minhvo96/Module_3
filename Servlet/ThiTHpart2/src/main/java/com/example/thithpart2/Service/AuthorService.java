package com.example.thithpart2.Service;

import com.example.thithpart2.DAO.AuthorDAO;
import com.example.thithpart2.Model.Author;

import java.util.List;

public class AuthorService {
    public static List<Author> getAuthors(){
        return new AuthorDAO().getAuthors();
    }
}

