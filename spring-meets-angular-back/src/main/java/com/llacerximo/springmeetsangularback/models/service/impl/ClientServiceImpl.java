package com.llacerximo.springmeetsangularback.models.service.impl;

import com.llacerximo.springmeetsangularback.models.dao.ClientDao;
import com.llacerximo.springmeetsangularback.models.entity.Client;
import com.llacerximo.springmeetsangularback.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientDao.findById(id).orElse(null);
    }

    @Override
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    public void delete(Long id) {
        clientDao.deleteById(id);
    }
}
