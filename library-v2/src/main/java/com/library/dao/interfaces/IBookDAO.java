package com.library.dao.interfaces;

import com.library.domain.Book;

import java.util.List;

/**
* Criar a lógica de adicionar informação padrão em publisher e author: "não identificado",
 * para posteriormente modificar ao adicionar publishers e authors.
* */
public interface IBookDAO {
    public Integer createBook(Book book);
    public Integer updateBook(Book book);
    public List<Book> searchAllBooks();
    public Book searchBookById(Long id);
    public Integer deleteBookById(Long id);
}