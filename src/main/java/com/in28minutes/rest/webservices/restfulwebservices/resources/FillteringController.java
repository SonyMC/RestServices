package com.in28minutes.rest.webservices.restfulwebservices.resources;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.rest.webservices.restfulwebservices.samplebeans.SampleFilterBean;

@RestController
public class FillteringController {
	
	
	// only send field 1 and field 2
	@GetMapping("/filter")
	public MappingJacksonValue retrieveSampleFilterBean(){
		
		SampleFilterBean sampleFilterBean = new SampleFilterBean("value1","value2","value3");
		
		
		
		//Create filters
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("SampleBeanFilter", simpleBeanPropertyFilter);
		
		
		// Use Jackson mapper for the filters 
		MappingJacksonValue mapper = new MappingJacksonValue(sampleFilterBean);
		mapper.setFilters(filters);
		
		
		return mapper;
	}

	
	
	// ony send field 1
	@GetMapping("/filterlist")
	public MappingJacksonValue retrieveSampleFilterBeanList(){
		
		List<SampleFilterBean> SampleBeanList = Arrays.asList(new SampleFilterBean("value1", "value2", "value3"),new SampleFilterBean("value4", "value5", "value6"));

        
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SampleBeanFilter", simpleBeanPropertyFilter);
		
		
		//Use Jackson Mapper for teh filters
		MappingJacksonValue mapper = new MappingJacksonValue(SampleBeanList);
		mapper.setFilters(filters);
		
		
		return mapper;

	}

	
	
}
