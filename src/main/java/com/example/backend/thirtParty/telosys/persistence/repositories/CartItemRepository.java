package com.example.backend.thirtParty.telosys.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.thirtParty.telosys.persistence.entities.CartItem;
import com.example.backend.thirtParty.telosys.persistence.entities.CartItemId;


/**
 * Spring Data JPA repository for entity "CartItem" <br> 
 * 
 * This repository extends PagingAndSortingRepository interface <br>
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
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    List<CartItem> findByUserId(long userId);


	// Insert specific finders here 

	//List<CartItem> findByXxx(String xxx);

	//List<CartItem> findByXxxStartingWith(String xxx);

	//List<CartItem> findByXxxContaining(String xxx);

	//List<CartItem> findByYyy(BigDecimal yyy);

	//List<CartItem> findByXxxContainingAndYyy(String xxx, BigDecimal yyy);
}
