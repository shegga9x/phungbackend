/*
 * Created on 2025-02-08 ( 14:50:02 )
 * Generated by Telosys ( https://www.telosys.org/ ) version 4.2.0
 */
package com.example.backend.telosys.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.telosys.rest.dto.AuthorsDTO;
import com.example.backend.telosys.rest.services.AuthorsService;

@RestController
@RequestMapping(value = "/api/v1/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorsRestController {

	private static final Logger logger = LoggerFactory.getLogger(AuthorsRestController.class);
	
	private AuthorsService service ; // injected
	
	/**
	 * Constructor (usable for Dependency Injection)
	 *
	 * @param service
	 */
	protected AuthorsRestController(AuthorsService service) {
		super();
		this.service = service;
	}
    
	/**
	 * Get ALL
	 *
	 * @return
	 */
	@GetMapping("")
	protected ResponseEntity<List<AuthorsDTO>> findAll() {
    	logger.debug("REST : GET - findAll");
    	List<AuthorsDTO> list = service.findAll();
    	return ResponseEntity.ok(list); // always 200
    }
    
    /**
     * Get ONE identified by the given PK
	 *
	 * @param id
     * @return 200 or 404 
     */
    @GetMapping("/{id}")
    protected ResponseEntity<AuthorsDTO> findById(@PathVariable int id) {
    	logger.debug("REST : GET - findById");
    	AuthorsDTO authorsDTO = service.findById(id);
		if ( authorsDTO != null ) {
			return ResponseEntity.ok(authorsDTO); // 200 OK, found
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found
		}		
    }

    
	/**
 	 * Create if doesn't exist 
	 *
	 * @param authorsDTO
	 * @return 201 created or 409 conflict
	 */
	@PostMapping("")
	protected ResponseEntity<Void> create(@RequestBody AuthorsDTO authorsDTO) {
    	logger.debug("REST : POST - create");
		if ( service.create(authorsDTO) ) {
			return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 created
		}
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
		}
	}

	/**
	 * Update or create
	 *
	 * @param id
	 * @param authorsDTO
	 * @return 200 updated or created
	 */
	@PutMapping("/{id}")
	protected ResponseEntity<Void> save(@PathVariable int id, @RequestBody AuthorsDTO authorsDTO) {
    	logger.debug("REST : PUT - save");
		service.save(id, authorsDTO);
		return ResponseEntity.ok().build(); // OK, updated or created
	}

	/**
 	 * Update if exists 
	 *
	 * @param authorsDTO
	 * @return 200 updated or 404 not found
	 */
	@PutMapping("")
	protected ResponseEntity<Void> update(@RequestBody AuthorsDTO authorsDTO) {
    	logger.debug("REST : PUT - update");
		if ( service.update(authorsDTO) ) {
			return ResponseEntity.ok().build(); // 200 OK, found and updated
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not updated"
		}
	}

	/**
 	 * Partial update for the given PK (if it exists )
	 *
	 * @param id
	 * @param authorsDTO
	 * @return 200 updated or 404 not found
	 */
	@PatchMapping("/{id}")
	protected ResponseEntity<Void> partialUpdate(@PathVariable int id, @RequestBody AuthorsDTO authorsDTO) {
    	logger.debug("REST : PATCH - partialUpdate");
    	if ( service.partialUpdate(id, authorsDTO) ) {
    		return ResponseEntity.ok().build(); // OK, found and updated
    	}
    	else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not updated"
    	}
	}

	/**
	 * Delete by PK 
	 *
	 * @param id
	 * @return 204 deleted or 404 not found
	 */
	@DeleteMapping("/{id}")
	protected ResponseEntity<Void> deleteById(@PathVariable int id) {
    	logger.debug("REST : DELETE - deleteById");
		if ( service.deleteById(id) ) {
			return ResponseEntity.noContent().build(); // 204 No content = "deleted"
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not deleted"
		}
	}

}
