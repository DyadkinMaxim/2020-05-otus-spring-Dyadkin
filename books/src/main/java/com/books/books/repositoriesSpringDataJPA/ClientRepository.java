package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByLogin(String login);
}
