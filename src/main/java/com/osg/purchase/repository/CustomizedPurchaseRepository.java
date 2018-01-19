package com.osg.purchase.repository;

import java.util.List;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.form.PurchaseCriteriaForm;

public interface CustomizedPurchaseRepository  {

	List<PurchaseEntity> findPurchases(PurchaseCriteriaForm criteria); 
	
}
