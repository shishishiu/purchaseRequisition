package com.osg.purchase.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "table_purchases_items")
@Table(name = "table_purchases_items")
public class PurchaseItemEntity {
	
	
    @ManyToOne
    @JoinColumn(name = "purchaseId", referencedColumnName="purchaseId", insertable = false, updatable = false)
    private PurchaseEntity purchaseEntity;

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = false)
    private String purchaseItemId;

    @Column(nullable = false, unique = false)
    private String purchaseId;

    @Column
    private int quantity;

    @Column
    private String unit;

    @Column
    private String productName;
    
    @Column
    private String brand;

    @Column
    private String note;
    
    @Column
    private int unitPrice;

    @Column
    private int totalPrice;

    @Column
    private String currency;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;

    

    public int getId()
   {
       return this.id;
   }

    public String getPurchaseItemId()
   {
       return this.purchaseItemId;
   }

    public String getPurchaseId()
   {
       return this.purchaseId;
   }

    public int getQuantity()
   {
       return this.quantity;
   }

    public String getUnit()
   {
       return this.unit;
   }

    public String getProductName()
   {
       return this.productName;
   }

    public String getBrand()
   {
       return this.brand;
   }

    public String getNote()
   {
       return this.note;
   }

    public int getUnitPrice()
   {
       return this.unitPrice;
   }

    public int getTotalPrice()
   {
       return this.totalPrice;
   }

    public String getCurrency()
   {
       return this.currency;
   }

   public Timestamp getCreatedAt()
  {
      return this.createdAt;
  }

   public Timestamp getUpdatedAt()
  {
      return this.updatedAt;
  }
   public PurchaseEntity getPurchase() {
	   return this.purchaseEntity;
   }
   	 
   public void setPurchase(PurchaseEntity purchaseEntity) {
	   this.purchaseEntity = purchaseEntity;
   }

}
