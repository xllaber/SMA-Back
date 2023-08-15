package com.llacerximo.springmeetsangularback.models.dao;

import com.llacerximo.springmeetsangularback.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientDao extends CrudRepository<Client, Long> {}
