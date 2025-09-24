package com.library.dao.implementations;

import com.library.dao.interfaces.IBookDAO;
import com.library.domain.Book;

import java.util.List;

public class BookDAOImpl implements IBookDAO {

    @Override
    public Integer createBook(Book book) {
        return 0;
    }

    @Override
    public Integer updateBook(Book book) {
        return 0;
    }

    @Override
    public List<Book> searchAllBooks() {
        return List.of();
    }

    @Override
    public Book searchBookById(Long id) {
        return null;
    }

    @Override
    public Integer deleteBookById(Long id) {
        return 0;
    }
}