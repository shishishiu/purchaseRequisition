package com.osg.purchase.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	
	public enum Currency {
	    MXP("MXP"),
	    USD("USD"),
		JPY("JPN");

		private final String value;

		Currency(String value)
		{
			this.value = value;
		}	
	    public String getValue() {
	        return value;
	    }

	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = false)
    private int purchaseItemId;

    @Column(nullable = false, unique = false)
    private int purchaseId;

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
    private String applicationArea;

    @Column
    private int isDeleted;

    @Column(insertable = false)
    private Timestamp createdAt;
    @Column
    private String createdUserId;

    @Column(insertable = false)
    private Timestamp updatedAt;

    @Column
    private String updatedUserId;

    public int getPurchaseItemId()
   {
       return this.purchaseItemId;
   }
    public void setPurchaseItemId(int purchaseItemId) {
 	   this.purchaseItemId = purchaseItemId;
    }

    public int getPurchaseId()
   {
       return this.purchaseId;
   }
    public void setPurchaseId(int purchaseId) {
  	   this.purchaseId = purchaseId;
     }

    public int getQuantity()
   {
       return this.quantity;
   }
    public void setQuantity(int quantity) {
   	   this.quantity = quantity;
      }

    public String getUnit()
   {
       return this.unit;
   }
    public void setUnit(String unit) {
   	   this.unit = unit;
      }

    public String getProductName()
   {
       return this.productName;
   }
    public void setProductName(String productName) {
    	   this.productName = productName;
       }

    public String getBrand()
   {
       return this.brand;
   }
    public void setBrand(String brand) {
    	   this.brand = brand;
       }

    public String getNote()
   {
       return this.note;
   }
    public void setNote(String note) {
 	   this.note = note;
    }

    public int getUnitPrice()
   {
       return this.unitPrice;
   }
    public void setUnitPrice(int unitPrice) {
  	   this.unitPrice = unitPrice;
     }

    public int getTotalPrice()
   {
       return this.totalPrice;
   }
    public void setTotalPrice(int totalPrice) {
   	   this.totalPrice = totalPrice;
      }

    public String getCurrency()
   {
       return this.currency;
   }
    public void setCurrency(String currency) {
  	   this.currency = currency;
     }

    public String getApplicationArea()
   {
       return this.applicationArea;
   }
    public void setApplicationArea(String applicationArea) {
  	   this.applicationArea = applicationArea;
     }

    public int getIsDeleted()
    {
        return this.isDeleted;
    }
    public void setIsDeleted(int isDeleted) {
   	   this.isDeleted = isDeleted;
      }

  public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
  public Timestamp getCreatedAt()
  {
      return this.createdAt;
  }
   public void setCreatedUserId(String createdUserId) {
       this.createdUserId = createdUserId;
   }
   public String getCreatedUserId()
  {
      return this.createdUserId;
  }

   public void setUpdatedAt(Timestamp updatedAt) {
       this.updatedAt = updatedAt;
   }
   public Timestamp getUpdatedAt()
  {
      return this.updatedAt;
  }
   public void setUpdatedUserId(String updatedUserId) {
       this.updatedUserId = updatedUserId;
   }
   public String getUpdatedUserId()
  {
      return this.updatedUserId;
  }
   public PurchaseEntity getPurchase() {
	   return this.purchaseEntity;
   }
   	 
   public void setPurchase(PurchaseEntity purchaseEntity) {
	   this.purchaseEntity = purchaseEntity;
   }

}
