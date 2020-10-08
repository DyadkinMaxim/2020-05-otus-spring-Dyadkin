drop table if exists books;
create table books (
    ID LONG PRIMARY KEY,
    NAME VARCHAR(255),
    AUTHOR VARCHAR(255),
    STYLE VARCHAR(255)
);

drop table if exists styles;
create table styles (
    ID LONG PRIMARY KEY,
    STYLE VARCHAR(255)
);

drop table if exists authors;
create table authors (
    ID LONG PRIMARY KEY,
    AUTHOR VARCHAR(255)
);