/*
 * http://localhost:8080/FirstSpringMVCProject/welcome/USA/Alex
 * http://www.gontu.org/spring-mvc-tutorials-09-pathvariable-annotation-detail-uri-template-concept/
 * https://www.youtube.com/watch?v=dDWNTR0-rns
 * 
 */
package com.gontuseries.hellocontroller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping("/welcome/{countryName}/{userName}")
	public ModelAndView helloWorld(@PathVariable Map<String,String> pathVars) {

		String name = pathVars.get("userName");
		String country = pathVars.get("countryName");

		ModelAndView model = new ModelAndView("HelloPage");
		model.addObject("msg","hello "+name+ " You are from "+country);

		return model;
	}
}