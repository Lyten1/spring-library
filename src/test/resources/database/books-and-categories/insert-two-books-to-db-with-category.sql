INSERT INTO categories (id, name, is_deleted)
VALUES (1, 'fantasy', false),
       (2, 'horror', false);

INSERT INTO books (id, title, author, price, isbn, is_deleted)
VALUES
    (1, 'hobit', 'D. Lois', 12.99, '123532421-1234', false),
    (2, 'man', 'D. Peter', 16.89, '54325654-1235', false);

INSERT INTO books_categories (book_id, category_id)
VALUES
    (1, 1),
    (2, 1);