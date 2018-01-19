package com.osg.purchase.form;

import java.io.Serializable;


public class PurchaseCriteriaForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private String purchaseId;
	private String solicitanteId;
    private String applicatedAtFrom;
    private String deliveryDateFrom;
    private String applicatedAtTo;
    private String deliveryDateTo;
	
    public void setPId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
	public String getPId(){
		return this.purchaseId;
	}
    public void setSolicitanteId(String solicitanteId) {
        this.solicitanteId = solicitanteId;
    }
	public String getSolicitanteId(){
		return this.solicitanteId;
	}

    public void setApplicatedAtFrom(String applicatedAtFrom) {
        this.applicatedAtFrom = applicatedAtFrom;
    }
    public String getApplicatedAtFrom(){
		return this.applicatedAtFrom;
	}

    public void setDeliveryDateFrom(String deliveryDateFrom) {
        this.deliveryDateFrom = deliveryDateFrom;
    }
    public String getDeliveryDateFrom(){
		return this.deliveryDateFrom;
	}
    public void setApplicatedAtTo(String applicatedAtTo) {
        this.applicatedAtTo = applicatedAtTo;
    }
    public String getApplicatedAtTo(){
		return this.applicatedAtTo;
	}

    public void setDeliveryDateTo(String deliveryDateTo) {
        this.deliveryDateTo = deliveryDateTo;
    }
    public String getDeliveryDateTo(){
		return this.deliveryDateTo;
	}


}
