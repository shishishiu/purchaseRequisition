package com.osg.purchase.form;

import java.io.Serializable;

import com.osg.purchase.customValidation.StringDateFormat;


public class PurchaseHeaderDeliveredDateForm implements Serializable{

	private static final long serialVersionUID = 1L;

    @StringDateFormat(pattern="yyyy-MM-dd", message="{err.msg.dateformat}")
	private String deliveredDate;
	
    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
    public String getDeliveredDate(){
		return this.deliveredDate;
	}

}
