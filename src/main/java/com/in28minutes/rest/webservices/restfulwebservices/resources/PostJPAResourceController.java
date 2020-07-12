

package com.in28minutes.rest.webservices.restfulwebservices.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.exception.PostNotCreatedException;
import com.in28minutes.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostRepository;


@RestController
public class PostJPAResourceController {
	
//	@Autowired
//	private UserRepository userRepository;
	
	//private UserJPAResourceController userJPAResourceController = new UserJPAResourceController(); 
	
	@Autowired
	private PostRepository postRepository;
	
		
	// Get all posts 
	@GetMapping(path="/jpa/posts")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Post>  retrieveAllPosts(){
		
		List<Post> posts = postRepository.findAll();
		
		if(posts.isEmpty()){
			throw new PostNotFoundException("No posts Found!!!");
		}
		
		return posts;
	}
	

	
	
	// get a particular post 
	@GetMapping(path="/jpa/posts/post/{postID}")
	@ResponseStatus(HttpStatus.OK)
	public RepresentationModel<Post> retrievePost(@PathVariable int postID){
			
		   
	    Optional<Post> optPost = postRepository.findById(postID);
	    
		if(!optPost.isPresent())
			throw new PostNotFoundException("Post ID: " + postID + " not found!!!");
	  
	    Post modelPost = optPost.get();
		
	    
		// create the self link  pointing to desired method 		
		Link linkto = linkTo(methodOn(this.getClass()).retrieveAllPosts()).withSelfRel();
		
		
		// The add method is inherirted from the RepresentationModel class which is super class of Collection Model which in turn was inherited bu Post class
		modelPost.add(linkto.withRel("all-posts"));
		
		return modelPost;
		
	
	}
	
	
	
	// delete a particular post 
	@GetMapping(path="/jpa/posts/deletepost/{postID}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> deleteUser(@PathVariable int postID){
		
		postRepository.deleteById(postID);

		
		return ResponseEntity.noContent().build();
		
	}
	
	
	// create a POST
	// input -> details of the POST
	// output -> CREATED & Return teh created uri
	
		@PostMapping("/jpa/posts")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<Object> createPost(@Valid @RequestBody Post post){
		
			

			// Save the post
			 Post savedPost = postRepository.save(post);
		
		
			 if(savedPost == null){
				 throw new PostNotCreatedException("Post could not be Created. Please check Request Format!!!");
			 }
		
		
	//	return Status and uri .Can be done using REsponse entity 
	// location uri needs to be created first ; take current uri and append new user id 
	// /users/{id} ->  replace id with user.getId
		
			 URI postLocation = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/post/{postID}")
						.buildAndExpand(savedPost.getId())
						.toUri();

		
			 return ResponseEntity.created(postLocation).build();
		
		
		
	}

}


