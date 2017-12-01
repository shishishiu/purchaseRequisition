package com.osg.purchase.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.osg.purchase.form.PurchaseCriteria;
import com.osg.purchase.util.DateUtil;

@Entity(name = "table_purchases")
@Table(name = "table_purchases")
public class PurchaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "purchaseEntity")
    private List<PurchaseItemEntity> purchaseItemList;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName="userId", insertable = false, updatable = false)
    private MemberEntity member;

	@Transient
	private final int DAYS_UNTIL_DELIVERY_DATE = 5;
	public enum IsDomesticValue {
		NATIONAL(1, "National"),
	    IMPORTADO(2, "Importado");

		private final int cd;
		private final String value;

		IsDomesticValue(int cd, String value)
		{
			this.cd = cd;
			this.value = value;
		}	
		public Integer getCode() {
	        return cd;
	    }
	    public String getValue() {
	        return value;
	    }

	}
	public enum CompanyValue {
		CAMPRO(1, "Campro"),
	    ROYCO(2, "Royco");

		private final int cd;
		private final String value;

		CompanyValue(int cd, String value)
		{
			this.cd = cd;
			this.value = value;
		}	
		public Integer getCode() {
	        return cd;
	    }
	    public String getValue() {
	        return value;
	    }

	}
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = false)
    private String purchaseId;

    @Column
    private String userId;

    @Column
    private String applicatedAt;

    @Column
    private String deliveryDate;

    @Column
    private int isDomestic;
    
    @Transient
    private IsDomesticValue isDomesticValue;
    
    @Column
    private int company;
    
    @Transient
    private CompanyValue companyValue;

    @Transient
    private boolean isNearDeliveryDate;

    @Column
    private String machineNo;

    @Column(nullable = false)
    private int isDeleted;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;

    @Transient
    private PurchaseCriteria purchaseCriteria;


    public int getId()
   {
       return this.id;
   }

    public String getPurchaseId()
   {
       return this.purchaseId;
   }

    public String getUserId()
   {
       return this.userId;
   }

    public String getApplicatedAt()
   {
       return this.applicatedAt;
   }

    public String getDeliveryDate()
   {
       return this.deliveryDate;
   }

    public int getIsDomestic()
   {
       return this.isDomestic;
   }
    public String getIsDomesticValue()
   {
    	if(this.isDomestic == IsDomesticValue.NATIONAL.getCode()) return IsDomesticValue.NATIONAL.getValue();
    	else if(this.isDomestic == IsDomesticValue.IMPORTADO.getCode()) return IsDomesticValue.IMPORTADO.getValue();
    	else return null;
   }

    public int getCompany()
   {
       return this.company;
   }
    public String getCompanyValue()
   {
    	if(this.company == CompanyValue.CAMPRO.getCode()) return CompanyValue.CAMPRO.getValue();
    	else if(this.company == CompanyValue.ROYCO.getCode()) return CompanyValue.ROYCO.getValue();
    	else return null;
   }

    public String getMachineNo()
   {
       return this.machineNo;
   }

   public int getEnable()
   {
       return this.isDeleted;
   }

   public Timestamp getCreatedAt()
  {
      return this.createdAt;
  }

   public Timestamp getUpdatedAt()
  {
      return this.updatedAt;
  }
   public MemberEntity getMember() {
   	return this.member;
	}
   	 
	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public List<PurchaseItemEntity> getPurchaseItemList() {
	   return this.purchaseItemList;
	}

	public void setPurchaseItemList(List<PurchaseItemEntity> purchaseItemList) {
		this.purchaseItemList = purchaseItemList;
	}

	public PurchaseCriteria getCriteria()
   {
       return this.purchaseCriteria;
   }
	public void setCriteria(PurchaseCriteria purchaseCriteria) {
		this.purchaseCriteria = purchaseCriteria;
	}

	public boolean getIsNearDeliveryDate() {
		
		Date dateTo = new Date();
		Date dateFrom = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			dateTo = sdf.parse(this.deliveryDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return DateUtil.SubtractDates(dateFrom, dateTo) <= DAYS_UNTIL_DELIVERY_DATE;
				
	}
}
