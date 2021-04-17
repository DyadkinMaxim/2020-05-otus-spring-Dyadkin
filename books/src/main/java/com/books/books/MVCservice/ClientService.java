package com.books.books.MVCservice;

import com.books.books.models.Client;
import com.books.books.repositoriesSpringDataJPA.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client= clientRepository.findByLogin(login);
        if (client == null) {
            throw new UsernameNotFoundException("Unknown client: "+login);
        }
        UserDetails user = User.builder()
                .username(client.getLogin())
                .password(client.getPassword())
                .roles(client.getRole())
                .build();
        return user;
    }
}
