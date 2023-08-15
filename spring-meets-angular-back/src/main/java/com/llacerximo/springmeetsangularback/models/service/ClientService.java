package com.llacerximo.springmeetsangularback.models.service;

import com.llacerximo.springmeetsangularback.models.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client save(Client client);
    void delete(Long id);
    Client findById(Long id);
}
