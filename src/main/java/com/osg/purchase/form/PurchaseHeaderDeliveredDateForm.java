package com.osg.purchase.form;

import java.io.Serializable;

import com.osg.purchase.customValidation.StringDateFormat;


public class PurchaseHeaderDeliveredDateForm implements Serializable{

	private static final long serialVersionUID = 1L;

    @StringDateFormat(pattern="yyyy-MM-dd", message="{err.msg.dateformat}")
	private String deliveredDate;
	private int isDelivered=1;
	
    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
    public String getDeliveredDate(){
		return this.deliveredDate;
	}
    public void setIsDelivered(int isDelivered) {
        this.isDelivered = isDelivered;
    }
   public int getIsDelivered()
   {
 	  return this.isDelivered;
   }


}
