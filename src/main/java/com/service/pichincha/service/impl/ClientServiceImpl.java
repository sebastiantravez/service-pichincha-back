package com.service.pichincha.service.impl;

import com.service.pichincha.dto.ClientDTO;
import com.service.pichincha.entities.Client;
import com.service.pichincha.exception.GenericException;
import com.service.pichincha.repository.ClientRepository;
import com.service.pichincha.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(ClientDTO clientDTO) {
        Optional<Client> client = clientRepository.findByDni(clientDTO.getDni());
        if (client.isPresent()) {
            throw new GenericException("Cliente ya existe");
        }
        Client saveClient = new Client();
        saveClient.setFullName(clientDTO.getFullName());
        saveClient.setGenderPerson(clientDTO.getGenderPerson());
        saveClient.setAge(clientDTO.getAge());
        saveClient.setDni(clientDTO.getDni());
        saveClient.setIdentificationPattern(clientDTO.getIdentificationPattern());
        saveClient.setAddress(clientDTO.getAddress());
        saveClient.setPhone(clientDTO.getPhone());
        saveClient.setPassword(clientDTO.getPassword());
        saveClient.setStatus(Boolean.TRUE);
        saveClient.setCreateDate(new Date());
        clientRepository.save(saveClient);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client client = clientRepository.findByDni(clientDTO.getDni())
                .orElseThrow(() -> new GenericException("Cliente no existe, no se puede actualizar datos"));
        client.setFullName(clientDTO.getFullName());
        client.setGenderPerson(clientDTO.getGenderPerson());
        client.setAge(clientDTO.getAge());
        client.setDni(clientDTO.getDni());
        client.setIdentificationPattern(clientDTO.getIdentificationPattern());
        client.setAddress(clientDTO.getAddress());
        client.setPhone(clientDTO.getPhone());
        client.setStatus(clientDTO.getStatus());
        client.setPassword(clientDTO.getPassword());
        client.setCreateDate(new Date());
        clientRepository.save(client);
        return clientDTO;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<ClientDTO> clientDTOS = clientRepository.findAllClients().stream()
                .filter(client -> client.getStatus())
                .map(this::buildClienteDTO)
                .collect(Collectors.toList());
        return clientDTOS;
    }

    @Override
    public void deleteClientById(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new GenericException("Cliente no existe, no se puede eliminar"));
        client.setStatus(Boolean.FALSE);
        clientRepository.save(client);
    }

    private ClientDTO buildClienteDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

}
