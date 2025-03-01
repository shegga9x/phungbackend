package com.example.backend.thirtParty.telosys.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.thirtParty.telosys.persistence.entities.ShippingInfo;

/**
 * Spring Data JPA repository for entity "ShippingInfo" <br>
 * 
 * This repository extends PagingAndSortingRepository interface <br>
 * so it provides by default all the basic CRUD operations : <br>
 * findById, findAll, save, delete, etc <br>
 * with pagination and sorting : <br>
 * findAll(Pageable), findAll(Sort)<br>
 * 
 * This repository can be extended by adding specific "finders" methods<br>
 * To do so, see the "predicates conventions" for "derived query methods" in
 * Spring Data documentation
 * 
 * @author Telosys
 *
 */
public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Long> {

	// Insert specific finders here
	List<ShippingInfo> findByUserId(Long userId);
	// List<ShippingInfo> findByXxx(String xxx);

	// List<ShippingInfo> findByXxxStartingWith(String xxx);

	// List<ShippingInfo> findByXxxContaining(String xxx);

	// List<ShippingInfo> findByYyy(BigDecimal yyy);

	// List<ShippingInfo> findByXxxContainingAndYyy(String xxx, BigDecimal yyy);
}
