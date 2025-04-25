-- Create 'author' table
CREATE TABLE author (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create 'genre' table
CREATE TABLE genre (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create 'book' table with references to 'author'
CREATE TABLE book (
    id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    author_id INT,
    FOREIGN KEY (author_id) REFERENCES author(id)
);

-- Create 'book_genre' table to establish many-to-many relationship between books and genres
CREATE TABLE book_genre (
    book_id INT,
    genre_id INT,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (genre_id) REFERENCES genre(id)
);
