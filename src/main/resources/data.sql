INSERT INTO author (id, name) VALUES (1, 'J.K. Rowling');
INSERT INTO author (id, name) VALUES (2, 'George R.R. Martin');
INSERT INTO author (id, name) VALUES (3, 'Frank Herbert');

INSERT INTO genre (id, name) VALUES (1, 'Fantasy');
INSERT INTO genre (id, name) VALUES (2, 'Adventure');
INSERT INTO genre (id, name) VALUES (3, 'Science Fiction');

INSERT INTO book (id, title, description, price, author_id) VALUES (1, 'Harry Potter and the Sorcerer''s Stone', 'A young wizard embarks on a magical journey.', 29.99, 1);
INSERT INTO book (id, title, description, price, author_id) VALUES (2, 'Game of Thrones', 'A series of political intrigue and dragons.', 39.99, 2);
INSERT INTO book (id, title, description, price, author_id) VALUES (3, 'Dune', 'A saga of political and environmental intrigue on a desert planet.', 49.99, 3);

INSERT INTO book_genre (book_id, genre_id) VALUES (1, 1);
INSERT INTO book_genre (book_id, genre_id) VALUES (1, 2);

INSERT INTO book_genre (book_id, genre_id) VALUES (2, 1);
INSERT INTO book_genre (book_id, genre_id) VALUES (2, 2);

INSERT INTO book_genre (book_id, genre_id) VALUES (3, 3);
INSERT INTO book_genre (book_id, genre_id) VALUES (3, 2);