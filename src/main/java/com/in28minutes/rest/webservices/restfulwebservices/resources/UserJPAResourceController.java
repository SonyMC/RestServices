package com.in28minutes.rest.webservices.restfulwebservices.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotCreatedException;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.UserRepository;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;

@RestController
public class UserJPAResourceController {
	

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	
	//GET /users
	// retrieveAllUsers 
	
	@GetMapping(path="/jpa/users")
	@ResponseStatus(HttpStatus.FOUND)
	public List<User> retrieveAllUsers(){
		
		List<User> users = userRepository.findAll();
		if (users.isEmpty()){
			throw new UserNotFoundException("No users found!!!");
		}
		
		
		return users;
		
	}

	

	
	@GetMapping(path="/jpa/users/userpost/{userid}")
	@ResponseStatus(HttpStatus.FOUND)
	public CollectionModel<Post> retrieveUserPosts(@PathVariable int userid){
		
		//List<Post> userPostList = new ArrayList<>();	// need this to return a collection model 	
		
		
		
		Optional<User> userOpt = userRepository.findById(userid);
		
		if(!userOpt.isPresent())
			throw new UserNotFoundException("User ID: " + userid + " not found!!!");

		
		// get all posts for that user
		List<Post> userPostList = userOpt.get().getPosts();
		
		
		
		//Now iterate the loop and add the hateoas link 
		for(Post userPost:userPostList){
			
			Link selfLink = linkTo(methodOn(this.getClass()).retrieveUserPosts(userid)).withSelfRel();
			userPost.add(selfLink.withRel("Current Link:"));
	
		}
		
	
		// Now create the self link  pointing to all posts 		
		Link link2 = linkTo(methodOn(PostJPAResourceController.class).retrieveAllPosts()).withSelfRel().withRel("All Posts: ");
		
		
		// Create the collection model 
		CollectionModel<Post> result = CollectionModel.of(userPostList, link2);
	
		return result;
	}

		
		
	// retrieveUser(int id)
	@GetMapping(path="/jpa/users/user/{id}")
	@ResponseStatus(HttpStatus.FOUND)
	public RepresentationModel<User> retrieveUser(@PathVariable int id){
		
		
		Optional<User> userOpt = userRepository.findById( id);
		
		if(!userOpt.isPresent())
			throw new UserNotFoundException("User ID: " + id + " not found!!!");

		RepresentationModel<User> modelUser = userOpt.get(); // because we extended User class fro Hateoas

		
		
		if(modelUser == null){
			throw new UserNotFoundException("User ID: " + id + " not found!!!");
		}
		
		//HATEOAS - Hypermedia As the Engine OF the Application State 
		// retrieve all resources 

		// Note : REsource is now Entity Model -> refer Hateoas documentatin  

		
		//Resource was replaced by EntityModel, and ControllerLinkBuilder was replaced by WebMvcLinkBuilder
		//EntityModel – represents RepresentationModel containing only single entity and related links.
		
		
		// create the self link  pointing to desired method 		
		Link linkto = linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel();
		
	
		// The add method is inherirted from the RepresentationModel class which was inherited bu User class
		modelUser.add(linkto.withRel("all-users"));
				

		return modelUser;
	}

		
		
	// deleteUser(int id)
	@GetMapping(path="/jpa/users/deleteuser/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> deleteUser(@PathVariable int id){
		
		//Status status = userDaoService.deleteUser((long)id);
		
		userRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	// create a user 
	// input -> details of the user
	// output -> CREATED & Return teh create uri
	
		@PostMapping("/jpa/users")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		
		System.out.println("************** User create:  " + user)	;
		// We do not want Request to provide id 
		if(user.getId()!= null){
			throw new UserNotCreatedException("User could not be Created. Please check Request Format!!!");
		}
			
		User savedUser = userRepository.save(user)	;

			
		
		if(savedUser == null){
			throw new UserNotCreatedException("User could not be Created. Please check Request Format!!!");
		}
		
		
	
		
	//	return Status and uri .Can be done using REsponse entity 
	// location uri needs to be created first ; take current uri and append new user id 
	// /users/{id} ->  replace id with user.getId	
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/user/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
				
		
		
	}
	

		
		@PostMapping(path="/jpa/users/userpost/post/{userid}")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<Object> createUserPosts(@PathVariable int userid,@RequestBody Post post){
			
			// first retrieve teh user 
			
			Optional<User> userOpt = userRepository.findById(userid);
	
			
			if(!userOpt.isPresent())
				throw new UserNotFoundException("User ID: " + userid + " not found!!!");
			
			
			User user = userOpt.get();
			
			// map the input post to the saved user 
			post.setUser(user);
			
			//now save the post to the DB
			postRepository.save(post);
			
			
			//	return Status and uri .Can be done using REsponse entity 
			// location uri needs to be created first ; take current uri and append new user id 
					URI location = ServletUriComponentsBuilder
								.fromCurrentRequest()
								.path("/jpa/posts/post/{postID}")
								.buildAndExpand(post.getId())
								.toUri();
				
				return ResponseEntity.created(location).build();
			
		
		}		
		
	
}
