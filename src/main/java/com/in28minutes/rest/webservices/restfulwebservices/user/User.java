package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


// Extend the base model class using REpresentation Model so that it can be used with Hateoas


@ApiModel(description="Model for User	!!!")  // for swagger api 
@Entity
public class User extends RepresentationModel<User>{
	
	
	@Id
	@GeneratedValue
	private Integer id;
	
	
	@Size(min=2, message="Name should have alteast 2 characters")
	@ApiModelProperty(notes="Atleast 2 chars excpected")
	private String name;
	
	
	@PastOrPresent
	@ApiModelProperty(notes="Birthdate should not be in future")
	private Date birthDate;
	

	@OneToMany(mappedBy="user")  // same nae as given in Post fro user
	private List<Post> posts;
	
	


	public User() {

	}

	public User(int id, String name, Date birthDate) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
	
	

}
