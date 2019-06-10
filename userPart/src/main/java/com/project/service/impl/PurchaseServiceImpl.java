package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Purchase;
import com.project.repository.PurchaseRepository;
import com.project.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	PurchaseRepository purchaseRepository;
	
	public Purchase save(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}
	
	public List<Purchase> findAllByUserId(Long id) {
		return purchaseRepository.findAllByUserId(id);
	}

}
