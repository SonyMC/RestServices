package com.in28minutes.rest.webservices.restfulwebservices.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.versioning.Name;
import com.in28minutes.rest.webservices.restfulwebservices.versioning.PersonV1;
import com.in28minutes.rest.webservices.restfulwebservices.versioning.PersonV2;

@RestController
public class PersonVersioningController {
	
	
	// basic versioning which uses different uris for different versions of the service 
	@GetMapping("v1/person")
	public PersonV1 personV1(){
		return new PersonV1("Amitabh Bachan");
	}
	
	// basic versioning which uses different uris for different versions of the service 
	@GetMapping("v2/person")
	public PersonV2 personV2(){
		return new PersonV2(new Name("Jackie","Chan"));
	}
	
	
	
	
	// param versioning which uses param value ( /params?version=nnn) for different versions of the service 
	@GetMapping(value="/person/params", params="version=1")
	public PersonV1 paramV1(){
		return new PersonV1("Amitabh Bachan");
	}
	
	// param versioning which uses param value ( /params?version=nnn) for different versions of the service 
	@GetMapping(value="/person/params", params="version=2")
	public PersonV2 paramV2(){
		return new PersonV2(new Name("Jackie","Chan"));
	}
	
	// header versioning which uses header value ( /headers and supply values in header using postman or some other tool) for different versions of the service 
	@GetMapping(value="/person/headers", headers="X-API-VERSION=1")
	public PersonV1 headerV1(){
		return new PersonV1("Amitabh Bachan");
	}
	
	// header versioning which uses header value ( /headers and supply values in header using postman or some other tool) for different versions of the service 
	@GetMapping(value="/person/headers", headers="X-API-VERSION=2")
	public PersonV2 headerV2(){
		return new PersonV2(new Name("Jackie","Chan"));
	}
		
	// produces versioning which uses produces value ( /headers and supply values in header using postman or some other tool) for different versions of the service 
	@GetMapping(value="/person/produces", produces="application/Sony.app-v1+json")
	public PersonV1 producesV1(){
		return new PersonV1("Amitabh Bachan");
	}
	
	// produces versioning which uses produces value ( /headers and supply values in header using postman or some other tool) for different versions of the service 
	@GetMapping(value="/person/produces", produces="application/Sony.app-v2+json")
	public PersonV2 producesV2(){
		return new PersonV2(new Name("Jackie","Chan"));
	}
	
}
