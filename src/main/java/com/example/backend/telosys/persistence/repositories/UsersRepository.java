/*
 * Created on 2025-02-08 ( 14:50:02 )
 * Generated by Telosys ( https://www.telosys.org/ ) version 4.2.0
 */
package com.example.backend.telosys.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.telosys.persistence.entities.Users;

/**
 * Spring Data JPA repository for entity "Users" <br> 
 * 
 * This repository extends JpaRepository interface <br>
 * so it provides by default all the basic CRUD operations : <br>
 *   findById, findAll, save, delete, etc <br> 
 * with pagination and sorting : <br>
 *   findAll(Pageable), findAll(Sort)<br>
 * 
 * This repository can be extended by adding specific "finders" methods<br>
 * To do so, see the "predicates conventions" for "derived query methods" in Spring Data documentation
 * 
 * @author Telosys
 *
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

	// Insert specific finders here 

	//List<Users> findByXxx(String xxx);

	//List<Users> findByXxxStartingWith(String xxx);

	//List<Users> findByXxxContaining(String xxx);

	//List<Users> findByYyy(BigDecimal yyy);

	//List<Users> findByXxxContainingAndYyy(String xxx, BigDecimal yyy);
}
