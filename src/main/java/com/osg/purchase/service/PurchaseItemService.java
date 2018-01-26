package com.osg.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osg.purchase.entity.PurchaseItemEntity;
import com.osg.purchase.repository.PurchaseItemRepository;

@Service
public class PurchaseItemService {

	@Autowired
    private PurchaseItemRepository purchaseItemRepository;
	
	public void updatePurchaseItem(PurchaseItemEntity entity, String loggedInUserId) {
		
		purchaseItemRepository.updatePurchaseItem(entity.getQuantity(), entity.getUnit(), entity.getProductName(), 
    			entity.getBrand(), entity.getNote(), entity.getUnitPrice(), entity.getTotalPrice(), entity.getCurrency(), 
    			entity.getApplicationArea(), loggedInUserId, entity.getPurchaseItemId());
	}
	public void deleteLogical(String loggedInUserId, int purchaseItemId) {
		
		purchaseItemRepository.updateIsDeletedDelete(loggedInUserId, purchaseItemId);
	}

}
