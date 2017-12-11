package com.osg.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseEntity.IsDomesticValue;
import com.osg.purchase.repository.PurchaseRepository;
import com.osg.purchase.util.EnumUtils;

@Service
public class PurchaseService {

	@Autowired
    private PurchaseRepository purchaseRepository;
	
	public void updatePurchaseHeader(PurchaseEntity entity, String loggedInUserId) {
		
		IsDomesticValue isdomestic = EnumUtils.valueOf(IsDomesticValue.class, entity.getIsDomestic());
		
    	purchaseRepository.updatePurchaseHeader(entity.getApplicatedAt(), entity.getDeliveryDate(), 
    			isdomestic.getCode(), entity.getCompany(), entity.getMachineNo(), loggedInUserId, entity.getPurchaseId());

		
	}

}
