	package com.in28minutes.rest.webservices.restfulwebservices.resources;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.helloworld.HelloWorldBean;

// Controller which handles http requests


@RestController
public class HelloWorldController {
     
	@Autowired
	private MessageSource messageSource;
	
	
	//@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	
	@GetMapping(path = "/hello-world")
	public String helloWorld(){
		return "Hello World";
	}
	
	
	//hello-world-bean
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean(){
		return new HelloWorldBean("Hello World Bean!!!");
	}
	
	//hello-world-bean/path-variable/in28minutes 
	@GetMapping(path="/hello-world-bean/path-variable/{pathname}")
	public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String  pathname){
		return new HelloWorldBean(String.format("Hello World Bean!!!, %s", pathname));
	}
	
	
	
	//hello-world-bean-i18n
	@GetMapping(path = "/hello-world-bean-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale){
		return messageSource.getMessage("good.morning.message",null,locale);
	}
	
}
