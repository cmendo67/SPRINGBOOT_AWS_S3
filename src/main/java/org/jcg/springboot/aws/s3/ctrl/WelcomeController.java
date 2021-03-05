package org.jcg.springboot.aws.s3.ctrl;

import java.util.Collection;
import java.util.Map;

import org.jcg.springboot.aws.s3.serv.ClientService;
import org.jcg.springboot.aws.s3.util.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	 @Autowired
	    private ClientService clientService;
	 
	 @RequestMapping(method = RequestMethod.GET)
	    public Collection<Client> getAllClients(){
	        return clientService.getAllClients();
	    }
//	 @GetMapping(value= "/test")
//		public String index(@RequestParam Map<String, String> params, Model model) {	 
////		Client clients = clientctrl.getClientById(0, model);
//			model.addAttribute("ClientFname", "carlos");
//	    	model.addAttribute("ClientMinit", "A");
//	    	model.addAttribute("ClientLname", "Mendoza");
//	    	model.addAttribute("ClientId", "12345");
//	    	model.addAttribute("ClientBdate", "November");
//	    	model.addAttribute("ClientAddress", "12345");
//	    	model.addAttribute("ClientSex", "M");
//			return "test";
//		}
	
	 @GetMapping(value= "/{id}/test")
		public String index(@PathVariable("id") int id,Model model) {
//		 
		 Client clients = clientService.getClientById(id);
	    	model.addAttribute("ClientFname", clients.getFname());
	    	model.addAttribute("ClientMinit", clients.getMinit());
	    	model.addAttribute("ClientLname", clients.getLname());
	    	model.addAttribute("ClientId", clients.getId());
	    	model.addAttribute("ClientBdate", clients.getBdate());
	    	model.addAttribute("ClientAddress", clients.getAddress());
	    	model.addAttribute("ClientSex", clients.getSex());
//			model.addAttribute("ClientFname", "carlos");
//	    	model.addAttribute("ClientMinit", "A");
//	    	model.addAttribute("ClientLname", "Mendoza");
//	    	model.addAttribute("ClientId", "12345");
//	    	model.addAttribute("ClientBdate", "November");
//	    	model.addAttribute("ClientAddress", "12345");
//	    	model.addAttribute("ClientSex", "M");
			return "test";
		}
}
