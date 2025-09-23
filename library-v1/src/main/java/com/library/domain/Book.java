package com.library.domain;

import jakarta.persistence.*;
import com.library.domain.enums.Category;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//* Adicionar colunas de: edição, linguas, número de páginas.

@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    private BigInteger isbn;

    @Column(name = "published_at", nullable = false)
    private LocalDate published_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_publisher_fk",
            foreignKey = @ForeignKey(name = "fk_publisher_book"),
            referencedColumnName = "id",
            nullable = true
    )
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_author")
    )
    private List<Author> authors = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getIsbn() {
        return isbn;
    }

    public void setIsbn(BigInteger isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedAt() {
        return published_at;
    }

    public void setPublishedAt(LocalDate published_at) {
        this.published_at = published_at;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}