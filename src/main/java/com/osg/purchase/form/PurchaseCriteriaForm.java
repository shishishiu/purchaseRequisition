package com.osg.purchase.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PurchaseCriteriaForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
    private String applicatedAt;
    private String deliveryDate;
	
    public void setUserId(String userId) {
        this.userId = userId;
    }
	public String getUserId(){
		return this.userId;
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


}
