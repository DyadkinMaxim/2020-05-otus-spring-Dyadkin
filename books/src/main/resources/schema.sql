/* --ROLLBACK
 drop table if exists comments;
 drop table if exists books;
 drop table if exists styles;
 drop table if exists authors;
 drop table if exists clients;
 drop table if exists acl_entry;
 drop table if exists acl_object_identity;
 drop table if exists acl_sid;
 drop table if exists acl_class;
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

CREATE TABLE IF NOT EXISTS acl_sid (
      id bigint(20) NOT NULL AUTO_INCREMENT,
      principal tinyint(1) NOT NULL,
      sid varchar(100) NOT NULL,
      PRIMARY KEY (id),
      UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
      id bigint(20) NOT NULL AUTO_INCREMENT,
      class varchar(255) NOT NULL,
      PRIMARY KEY (id),
      UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
      id bigint(20) NOT NULL AUTO_INCREMENT,
      acl_object_identity bigint(20) NOT NULL,
      ace_order int(11) NOT NULL,
      sid bigint(20) NOT NULL,
      mask int(11) NOT NULL,
      granting tinyint(1) NOT NULL,
      audit_success tinyint(1) NOT NULL,
      audit_failure tinyint(1) NOT NULL,
      PRIMARY KEY (id),
      UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object bigint(20) DEFAULT NULL,
    owner_sid bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);