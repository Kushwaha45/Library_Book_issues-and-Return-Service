-- =============================================
-- Sample Data for Library Book Issue & Return Service
-- =============================================

-- Insert Books (only if table is empty)
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (1, 'Java: The Complete Reference', 'Herbert Schildt', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (2, 'Clean Code', 'Robert C. Martin', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (3, 'Design Patterns', 'Gang of Four', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (4, 'Spring in Action', 'Craig Walls', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (5, 'Head First Java', 'Kathy Sierra', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (6, 'Effective Java', 'Joshua Bloch', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (7, 'Data Structures and Algorithms', 'Robert Lafore', true);
INSERT IGNORE INTO books (book_id, title, author, available) VALUES (8, 'The Pragmatic Programmer', 'David Thomas', true);

-- Insert Members (only if table is empty)
INSERT IGNORE INTO members (member_id, name, email) VALUES (1, 'Aniket Sharma', 'aniket@example.com');
INSERT IGNORE INTO members (member_id, name, email) VALUES (2, 'Priya Patel', 'priya@example.com');
INSERT IGNORE INTO members (member_id, name, email) VALUES (3, 'Rahul Verma', 'rahul@example.com');
