INSERT INTO tb_category (category, category_code) VALUES
('Ficção Científica', 'FC001'),
('Romance', 'RM002'),
('História', 'HS003'),
('Filosofia', 'FS004'),
('Fantasia', 'FT005'),
('Suspense', 'SP006');

INSERT INTO tb_author (name, author_code) VALUES
('Isaac Asimov', 'A001'),
('Jane Austen', 'A002'),
('Yuval Noah Harari', 'A003'),
('Platão', 'A004'),
('J.R.R. Tolkien', 'A005'),
('Agatha Christie', 'A006'),
('Stephen King', 'A007'),
('Dan Brown', 'A008');

INSERT INTO tb_book (title, isbn, published_at, publisher, id_category_fk) VALUES
('Fundação', 9788535902772, '1951-06-01', 'Editora Aleph', 1),
('Orgulho e Preconceito', 9788578270698, '1813-01-28', 'Martin Claret', 2),
('Sapiens', 9788535920424, '2011-01-01', 'Companhia das Letras', 3),
('A República', 9788572326568, '0380-01-01', 'Editora Vozes', 4),
('O Senhor dos Anéis: A Sociedade do Anel', 9788595084742, '1954-07-29', 'HarperCollins', 5),
('O Senhor dos Anéis: As Duas Torres', 9788595084759, '1954-11-11', 'HarperCollins', 5),
('O Senhor dos Anéis: O Retorno do Rei', 9788595084766, '1955-10-20', 'HarperCollins', 5),
('Assassinato no Expresso do Oriente', 9788576861089, '1934-01-01', 'Globo Livros', 6),
('O Iluminado', 9788532511014, '1977-01-28', 'Suma', 6),
('O Código Da Vinci', 9788575422397, '2003-03-18', 'Sextante', 6);

-- Fundação - Asimov
INSERT INTO books_authors (id_book, id_author) VALUES (1, 1);

-- Orgulho e Preconceito - Austen
INSERT INTO books_authors (id_book, id_author) VALUES (2, 2);

-- Sapiens - Harari
INSERT INTO books_authors (id_book, id_author) VALUES (3, 3);

-- A República - Platão
INSERT INTO books_authors (id_book, id_author) VALUES (4, 4);

-- Trilogia Senhor dos Anéis - Tolkien
INSERT INTO books_authors (id_book, id_author) VALUES (5, 5);
INSERT INTO books_authors (id_book, id_author) VALUES (6, 5);
INSERT INTO books_authors (id_book, id_author) VALUES (7, 5);

-- Suspense: Christie, King, Dan Brown
INSERT INTO books_authors (id_book, id_author) VALUES (8, 6); -- Assassinato no Expresso do Oriente
INSERT INTO books_authors (id_book, id_author) VALUES (9, 7); -- O Iluminado
INSERT INTO books_authors (id_book, id_author) VALUES (10, 8); -- O Código Da Vinci