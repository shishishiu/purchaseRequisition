package com.osg.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.repository.CustomizedPurchaseRepository;

public interface PurchaseRepository extends CrudRepository<PurchaseEntity, Long>, JpaRepository<PurchaseEntity, Long>,
JpaSpecificationExecutor<PurchaseEntity>, CustomizedPurchaseRepository {
	
    public PurchaseEntity findByPurchaseId(String purchaseId);
    public List<PurchaseEntity> findByIsDeletedOrderByApplicatedAt(int isDeleted);
    
    @Modifying
    @Transactional
    @Query("UPDATE table_purchases SET applicated_at=?1, delivery_date=?2, is_domestic=?3, company=?4, machine_no=?5, updated_user_id=?6 WHERE purchase_id=?7")
    void updatePurchaseHeader(String applicatedAt, String deliveryDate, int isDomestic, 
    		int company, String machineNo, String userId, String purchaseId);

}
