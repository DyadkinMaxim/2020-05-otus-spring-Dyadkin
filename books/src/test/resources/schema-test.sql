/* --ROLLBACK
 drop table if exists books;
 drop table if exists styles;
 drop table if exists authors;
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