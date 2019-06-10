package com.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.domain.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{
	
	List<Purchase> findAllByUserId(Long id);

}
