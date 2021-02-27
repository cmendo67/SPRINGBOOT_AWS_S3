package com.example.accessingdatamysql.Dao;
import java.util.*;

import com.example.accessingdatamysql.Entity.Client;

public interface ClientDao {
	
	Collection<Client> getAllClients();
	
	Client getClientById(int id);

	void removeClienttById(int id);

    void updateClient(Client client);

    void insertClientToDb(Client client);
		
}
