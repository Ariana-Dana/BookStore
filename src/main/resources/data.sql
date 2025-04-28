-- Inserăm autori
INSERT INTO author (name) VALUES ('J.K. Rowling');
INSERT INTO author (name) VALUES ('George R.R. Martin');
INSERT INTO author (name) VALUES ('Frank Herbert');

-- Inserăm genuri
INSERT INTO genre (name) VALUES ('Fantasy');
INSERT INTO genre (name) VALUES ('Adventure');
INSERT INTO genre (name) VALUES ('Science Fiction');

-- Inserăm cărți
INSERT INTO book (title, description, price, author_id) VALUES 
('Harry Potter and the Sorcerer''s Stone', 'A young wizard embarks on a magical journey.', 29.99, 1),
('Game of Thrones', 'A series of political intrigue and dragons.', 39.99, 2),
('Dune', 'A saga of political and environmental intrigue on a desert planet.', 49.99, 3);

-- Asociem cărți cu genuri
INSERT INTO book_genre (book_id, genre_id) VALUES 
(1, 1),  -- Harry Potter - Fantasy
(1, 2),  -- Harry Potter - Adventure
(2, 1),  -- Game of Thrones - Fantasy
(2, 2),  -- Game of Thrones - Adventure
(3, 3),  -- Dune - Science Fiction
(3, 2);  -- Dune - Adventure

-- Inserăm clienți
INSERT INTO customer (name, email) VALUES 
('John Doe', 'johndoe@example.com'),
('Jane Smith', 'janesmith@example.com'),
('Alice Johnson', 'alice.johnson@example.com');

-- Inserăm comenzi
INSERT INTO `order` (customer_id, order_date, total) VALUES 
(1, '2025-04-28T10:00:00', 69.98),
(2, '2025-04-28T11:00:00', 79.98),
(3, '2025-04-28T12:00:00', 99.98);

-- Inserăm produse în comenzi
INSERT INTO order_item (order_id, book_id, quantity, price) VALUES 
(1, 1, 1, 29.99),  -- Comanda 1 - Harry Potter
(1, 2, 1, 39.99),  -- Comanda 1 - Game of Thrones
(2, 2, 1, 39.99),  -- Comanda 2 - Game of Thrones
(2, 3, 1, 49.99),  -- Comanda 2 - Dune
(3, 3, 1, 49.99),  -- Comanda 3 - Dune
(3, 1, 1, 29.99);  -- Comanda 3 - Harry Potter
