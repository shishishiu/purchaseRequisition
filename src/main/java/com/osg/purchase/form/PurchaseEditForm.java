package com.osg.purchase.form;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.osg.purchase.entity.PurchaseEntity.IsDomesticValue;

public class PurchaseEditForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private String purchaseId;

	@NotNull
    @Size(min = 1, max = 255, message="{err.msg.size}")
	private String userId;

	private String username;

	@NotNull
	private String applicatedAt;
	
	@NotNull
	private String deliveryDate;
	
    private int isDomestic;

    private String isDomesticValue;

    private int company;

    private String companyValue;

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
	public String getPurchaseId(){
		return this.purchaseId;
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

	public void setApplicatedAt(String applicatedAt) {
        this.applicatedAt = applicatedAt;
    }
    public String getApplicatedAt(){
		return this.applicatedAt;
	}

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getDeliveryDate(){
		return this.deliveryDate;
	}

    public void setIsDomestic(int isDomestic) {
        this.isDomestic = isDomestic;
    }
   public int getIsDomestic()
   {
       return this.isDomestic;
   }

   public void setIsDomesticValue(String isDomesticValue) {
       this.isDomesticValue = isDomesticValue;
   }
   public String getIsDomesticValue(){
		return this.isDomesticValue;
	}

   public void setCompany(int company) {
       this.company = company;
   }
  public int getCompany()
  {
      return this.company;
  }

  public void setCompanyValue(String companyValue) {
      this.companyValue = companyValue;
  }
  public String getCompanyValue(){
		return this.companyValue;
	}

}
