package org.jcg.springboot.aws.s3.serv;
import java.util.*;

import org.jcg.springboot.aws.s3.util.Client;


public interface ClientDao {
	
	Collection<Client> getAllClients();
	
	Client getClientById(int id);

	void removeClienttById(int id);

    void updateClient(Client client);

    void insertClientToDb(Client client);
		
}
