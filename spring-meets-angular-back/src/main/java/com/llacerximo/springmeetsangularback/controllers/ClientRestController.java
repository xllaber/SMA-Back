package com.llacerximo.springmeetsangularback.controllers;

import com.llacerximo.springmeetsangularback.models.entity.Client;
import com.llacerximo.springmeetsangularback.models.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<Client> index(){
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Client client;
        Map<String, Object> response = new HashMap<>();
        try {
            client = clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Database Error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (client == null) {
            response.put("message", "Client with id ".concat(id.toString()).concat(" doesn't exist"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> insert(@Valid @RequestBody Client client, BindingResult bindingResult) {
        Client newClient;
        Map<String, Object> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            for (FieldError error: bindingResult.getFieldErrors()) {
//                errors.add("Field " + error.getField() + " " + error.getDefaultMessage());
//            }
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> "Field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("message", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            newClient = clientService.save(client);
        } catch (DataAccessException e) {
            response.put("message", "Database Error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", "Client created successfully");
        response.put("Client", newClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Client client, BindingResult bindingResult, @PathVariable Long id) {
        Client currentClient = clientService.findById(id);
        Client updatedClient;
        Map<String, Object> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> "Field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("message", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (currentClient == null) {
            response.put("message", "Client with id ".concat(id.toString()).concat(" doesn't exist"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            currentClient.setLastName(client.getLastName());
            currentClient.setEmail(client.getEmail());
            currentClient.setName(client.getName());
            updatedClient = clientService.save(currentClient);
        } catch (DataAccessException e) {
            response.put("message", "Database Error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", "Client updated successfully");
        response.put("Client", updatedClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("clients/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            clientService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Database Error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", "Client deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
