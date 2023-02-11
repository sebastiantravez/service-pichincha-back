package com.service.pichincha.controller;

import com.service.pichincha.dto.ClientDTO;
import com.service.pichincha.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/saveClient")
    public void saveClient(@RequestBody ClientDTO clientDTO) {
        clientService.save(clientDTO);
    }

    @PutMapping("/updateClient")
    public void updateClient(@RequestBody ClientDTO clientDTO) {
        clientService.updateClient(clientDTO);
    }

    @GetMapping("/getAllClients")
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @DeleteMapping("/deleteClient")
    public void deleteClient(@RequestParam Long clientId) {
        clientService.deleteClientById(clientId);
    }

}
