package com.library.dao.implementations;

import com.library.dao.interfaces.IAuthorDAO;
import com.library.domain.Author;

import java.util.List;

public class AuthorDAOImpl implements IAuthorDAO {
    @Override
    public Integer createAuthor(Author author) {
        return 0;
    }

    @Override
    public Integer updateAuthor(Author author) {
        return 0;
    }

    @Override
    public List<Author> searchAllAuthors() {
        return List.of();
    }

    @Override
    public Author searchAuthorById(Long id) {
        return null;
    }

    @Override
    public Integer deleteAuthorById(Long id) {
        return 0;
    }
}