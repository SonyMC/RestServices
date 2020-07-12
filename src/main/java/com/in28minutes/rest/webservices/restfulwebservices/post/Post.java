package com.in28minutes.rest.webservices.restfulwebservices.post;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;

import io.swagger.annotations.ApiModel;


@ApiModel(description="Model for Post	!!!")  // for swagger api 
@Entity
public class Post extends RepresentationModel<Post>{
	
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)   // this it avoid recursive autimatic fetch of User and Post objects
	@JsonIgnore
	private User user;
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	// Note;  In the to string we have purposely omitted the User object to avoid recursive fetch.
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}

	
	

}
