package com.osg.purchase.repository;

import java.util.List;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.form.PurchaseCriteria;

public interface CustomizedPurchaseRepository  {

//	List<PurchaseEntity> findAllByUserId(PurchaseCriteriaForm criteria); 
	List<PurchaseEntity> findPurchases(PurchaseCriteria criteria); 
	
}
