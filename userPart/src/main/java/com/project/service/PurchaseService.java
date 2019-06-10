package com.project.service;

import java.util.List;

import com.project.domain.Purchase;

public interface PurchaseService {
	
	Purchase save(Purchase purchase);

	List<Purchase> findAllByUserId(Long id);

}
