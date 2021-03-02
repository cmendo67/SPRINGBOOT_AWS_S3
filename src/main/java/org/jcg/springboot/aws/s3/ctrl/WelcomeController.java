package org.jcg.springboot.aws.s3.ctrl;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	
	 @GetMapping(value= "/test")
		public String index(@RequestParam Map<String, String> params, Model model) {
			return "test";
		}
}
