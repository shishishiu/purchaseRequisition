package com.osg.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.repository.CustomizedPurchaseRepository;

public interface PurchaseRepository extends CrudRepository<PurchaseEntity, Long>, JpaRepository<PurchaseEntity, Long>,
JpaSpecificationExecutor<PurchaseEntity>, CustomizedPurchaseRepository {
	
    public PurchaseEntity findByPurchaseId(String purchaseId);
    public List<PurchaseEntity> findByIsDeletedOrderByApplicatedAt(int isDeleted);
    
}
