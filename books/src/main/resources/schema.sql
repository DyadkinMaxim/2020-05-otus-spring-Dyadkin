/* --ROLLBACK
 drop table if exists comments;
 drop table if exists books;
 drop table if exists styles;
 drop table if exists authors;
 drop table if exists clients;
 */

create table styles (
    ID LONG  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    STYLE VARCHAR(255)
);

create table authors (
    ID LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AUTHOR VARCHAR(255)
);

create table books (
    ID LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BOOK VARCHAR(255),
    AUTHOR_ID LONG,
    STYLE_ID LONG,
    FOREIGN KEY (AUTHOR_ID) REFERENCES authors(ID),
    FOREIGN KEY (STYLE_ID) REFERENCES styles(ID)
);

create table comments (
    ID LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    COMMENT VARCHAR(255),
    BOOK_ID LONG,
    FOREIGN KEY (BOOK_ID) REFERENCES books(ID)
);

create table CLIENTS
(
    ID      LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    LOGIN VARCHAR(255),
    PASSWORD VARCHAR(255),
    ROLE VARCHAR(255)
);