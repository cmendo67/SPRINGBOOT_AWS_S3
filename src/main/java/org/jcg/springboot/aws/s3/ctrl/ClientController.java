package org.jcg.springboot.aws.s3.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.jcg.springboot.aws.s3.util.Client;
import org.jcg.springboot.aws.s3.serv.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Client getClientById(@PathVariable("id") int id){
//    	Client clients = clientService.getClientById(id);
//    	
//    	model.addAttribute("Client Fname", clients.getFname());
//    	model.addAttribute("Client Minit", clients.getMinit());
//    	model.addAttribute("Client Lname", clients.getLname());
//    	model.addAttribute("Client Id", clients.getId());
//    	model.addAttribute("Client Bdate", clients.getBdate());
//    	model.addAttribute("Client Address", clients.getAddress());
//    	model.addAttribute("Client Sex", clients.getSex());

        return clientService.getClientById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteClientById(@PathVariable("id") int id){
    	clientService.removeClientById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteClienttById(@RequestBody Client client){
    	clientService.updateClient(client);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertClient(@RequestBody Client client){
    	clientService.insertClient(client);
    }
}