package com.example.accessingdatamysql.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.accessingdatamysql.Dao.ClientDao;
import com.example.accessingdatamysql.Entity.Client;

import java.util.Collection;

@Service
public class ClientService {

    @Autowired
    @Qualifier("mysql")
    private ClientDao clientDao;

    public Collection<Client> getAllClients(){
        return this.clientDao.getAllClients();
    }

    public Client getClientById(int id){
        return this.clientDao.getClientById(id);
    }


    public void removeClientById(int id) {
        this.clientDao.removeClienttById(id);
    }

    public void updateClient(Client client){
        this.clientDao.updateClient(client);
    }

    public void insertClient(Client client) {
        this.clientDao.insertClientToDb(client);
    }
}
