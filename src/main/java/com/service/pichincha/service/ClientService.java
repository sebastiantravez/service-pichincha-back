package com.service.pichincha.service;

import com.service.pichincha.dto.ClientDTO;

import java.util.List;

public interface ClientService {
    void save(ClientDTO clientDTO);

    ClientDTO updateClient(ClientDTO clientDTO);

    List<ClientDTO> getAllClients();

    void deleteClientById(Long clientId);

}
