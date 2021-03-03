package org.jcg.springboot.aws.s3.ctrl;

import java.util.Map;

import org.jcg.springboot.aws.s3.util.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	
	 @GetMapping(value= "/test")
		public String index(@RequestParam Map<String, String> params, Model model) {
//		 ClientController clientctrl = new ClientController();
		 
//		 Client clients = clientctrl.getClientById(0, model);
			model.addAttribute("Client Fname", "carlos");
	    	model.addAttribute("Client Minit", "carlos");
	    	model.addAttribute("Client Lname", "carlos");
	    	model.addAttribute("Client Id", "carlos");
	    	model.addAttribute("Client Bdate", "carlos");
	    	model.addAttribute("Client Address", "carlos");
	    	model.addAttribute("Client Sex", "carlos");
			return "test";
		}
}
