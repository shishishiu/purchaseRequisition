package com.osg.purchase.form;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.osg.purchase.customValidation.StringDateFormat;


public class PurchaseHeaderEditForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private int purchaseId;

    @Size(min = 1, max = 255)
	private String userId;

	private String username;

    @StringDateFormat(pattern="yyyy-MM-dd", message="{err.msg.dateformat}")
	private String applicatedAt = GetToday();
	
    @StringDateFormat(pattern="yyyy-MM-dd", message="{err.msg.dateformat}")
	private String deliveryDate;
	
	@NotNull
	@Min(value=1, message="{err.msg.requerido}")
    private int isDomestic;

    private String isDomesticValue;

	@NotNull
	@Min(value=1, message="{err.msg.requerido}")
    private int company;

    private String companyValue;
    @Size(min = 1, max = 255)
    private String machineNo;

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
	private String GetToday() {
    	SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
    	return d1.format(new Date());
	}
	public int getPurchaseId(){
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
  public void setMachineNo(String machineNo) {
      this.machineNo = machineNo;
  }
	public String getMachineNo(){
		return this.machineNo;
	}

}
