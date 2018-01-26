package com.osg.purchase.form;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PurchaseItemEditForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private int purchaseId;
	private int purchaseItemId;
    @Size(min = 1, max = 255)
	private String userId;
	private String username;
	@Min(value=1, message="{err.msg.requerido}")
 	private int quantity;
 	private String unit;
    private String productName;
    private String brand;
    @Size(max = 255, message="{err.msg.size.max}")
    private String note;
	@Min(value=1, message="{err.msg.requerido}")
 	private int unitPrice;
	@Min(value=1, message="{err.msg.requerido}")
 	private int totalPrice;
	@NotNull
	@Size(min=2, message="{err.msg.requerido}")
    private String currency;
	@Size(min=1, message="{err.msg.requerido}")
    private String applicationArea;

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
	public int getPurchaseId(){
		return this.purchaseId;
	}
  public void setPurchaseItemId(int purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }
	public int getPurchaseItemId(){
		return this.purchaseItemId;
	}

    public void setUserId(String userId) {
        this.userId = userId;
    }
	public String getUserId(){
		return this.userId;
	}

    public void setUsername(String username) {
        this.username = username;
    }
	public String getUsername(){
		return this.username;
	}

	public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity(){
		return this.quantity;
	}

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getUnit(){
		return this.unit;
	}

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName(){
		return this.productName;
	}

   public void setBrand(String brand) {
       this.brand = brand;
   }
   public String getBrand(){
		return this.brand;
	}

   public void setNote(String note) {
	   this.note = note;
   }
   public String getNote(){
	   return this.note;
   }

   public void setUnitPrice(int unitPrice) {
       this.unitPrice = unitPrice;
   }
  public int getUnitPrice()
  {
      return this.unitPrice;
  }
  public void setTotalPrice(int totalPrice) {
      this.totalPrice = totalPrice;
  }
 public int getTotalPrice()
 {
     return this.totalPrice;
 }
 public void setCurrency(String currency) {
     this.currency = currency;
 }
 public String getCurrency(){
		return this.currency;
	}
 public String getApplicationArea()
 {
    return this.applicationArea;
 }
 public void setApplicationArea(String applicationArea) {
   this.applicationArea = applicationArea;
  }


}
