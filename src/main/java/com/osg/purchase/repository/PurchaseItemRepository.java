package com.osg.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseItemEntity;

public interface PurchaseItemRepository extends CrudRepository<PurchaseItemEntity, Long>, JpaRepository<PurchaseItemEntity, Long>,
JpaSpecificationExecutor<PurchaseEntity> {
	
    public PurchaseItemEntity findByPurchaseItemId(int purchaseItemId);
    
    @Modifying
    @Transactional
    @Query("UPDATE table_purchases_items SET quantity=?1, unit=?2, product_name=?3, brand=?4, note=?5, unit_price=?6, total_price=?7, currency=?8, updated_user_id=?9 WHERE purchase_item_id=?10")
    public void updatePurchaseItem(int quantity, String unit, String productName, String brand, String note, int unitPrice, int totalPrice, 
    		String currency, String userId, int purchaseItemId);

    @Modifying
    @Transactional
    @Query("UPDATE table_purchases_items SET is_deleted=1, updated_user_id=?1 WHERE purchase_item_id=?2")
	public void updateIsDeletedDelete(String loggedInUserId, int purchaseItemId);

}
