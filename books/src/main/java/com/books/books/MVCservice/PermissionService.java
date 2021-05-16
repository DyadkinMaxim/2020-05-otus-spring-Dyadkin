package com.books.books.MVCservice;

import com.books.books.models.Book;
import org.springframework.security.acls.model.Permission;

public interface PermissionService {

    void addPermissionForUser(Book book, Permission permission, String username);
}
