--insert into authors
insert into authors values (1, 'Джек Лондон');
insert into authors values (2, 'Лев Толстой');
insert into authors values (3, 'Уильям Шекспир');
insert into authors values (4, 'Чарльз Дикенс');
insert into authors values (5, 'Александр Пушкин');

--insert into styles
insert into styles values (1, 'приключения');
insert into styles values (2, 'роман');
insert into styles values (3, 'трагедия');
insert into styles values (4, 'поэма');

--insert into books
insert into books values (1, 'Белый клык', 1, 1);
insert into books values (2, 'Война и мир', 2, 2);
insert into books values (3, 'Гамлет', 3, 3);
insert into books values (4, 'Дэвид Копперфильд', 4, 2);
insert into books values (5, 'Вольность', 5, 4);

--insert into comments
insert into comments values (1, 'гуд', 1);
insert into comments values (2, 'не гуд', 1);
insert into comments values (3, 'продам гараж, тел. +7-916-439-58-68', 2);
insert into comments values (4, 'Когда кончится ковид?', 3);
insert into comments values (5, 'ай да Пушкин, ай да...', 5);
insert into comments values (6, 'не читал, но осуждаю', 5);
insert into comments values (7, 'очень понравилось', 3);
insert into comments values (8, 'лажа', 4);

--insert into clients
insert into clients (LOGIN, PASSWORD, ROLE) values ('user1', 'password', 'user');
insert into clients (LOGIN, PASSWORD, ROLE) values ('user2', 'password', 'user');
insert into clients (LOGIN, PASSWORD, ROLE) values ('admin', 'password', 'admin');

--client's list
INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'admin'),
(2, 1, 'user1'),
(3, 1, 'user2');

--domain object
INSERT INTO acl_class (id, class) VALUES
(1, 'com.books.books.models.Book');

--default book list
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0),
(2, 1, 2, NULL, 3, 0),
(3, 1, 3, NULL, 3, 0),
(4, 1, 4, NULL, 3, 0),
(5, 1, 5, NULL, 3, 0);

--permissions for user1
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 2, 1, 1, 1, 1),
(2, 2, 2, 2, 1, 1, 1, 1),
(3, 3, 3, 2, 1, 1, 1, 1);

--permissions for user2
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(4, 4, 4, 3, 1, 1, 1, 1),
(5, 5, 5, 3, 1, 1, 1, 1);

--permissions for admin
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(6, 1, 6, 1, 1, 1, 1, 1),
(7, 2, 7, 1, 1, 1, 1, 1),
(8, 3, 8, 1, 1, 1, 1, 1),
(9, 4, 9, 1, 1, 1, 1, 1),
(10, 5, 10, 1, 1, 1, 1, 1);