package com.library.dao.interfaces;

import com.library.domain.Author;

import java.util.List;

public interface IAuthorDAO {
    public Integer createAuthor(Author author);
    public Integer updateAuthor(Author author);
    public List<Author> searchAllAuthors();
    public Author searchAuthorById(Long id);
    public Integer deleteAuthorById(Long id);
}