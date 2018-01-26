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

import com.osg.purchase.entity.interf.EnumInterface;
import com.osg.purchase.form.PurchaseCriteriaForm;
import com.osg.purchase.util.DateUtils;

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
	public enum IsDomesticValue implements EnumInterface {
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
    private int purchaseId;

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

    @Column
    private int isDelivered;
    
    @Column
    private String deliveredDate;


    @Column(nullable = false)
    private int isDeleted;

    @Column(insertable = false)
    private Timestamp createdAt;

    @Column
    private String createdUserId;

    @Column(insertable = false)
    private Timestamp updatedAt;

    @Column
    private String updatedUserId;

   @Transient
    private PurchaseCriteriaForm purchaseCriteria;

   public void setPurchaseId(int purchaseId) {
       this.purchaseId = purchaseId;
   }
    public int getPurchaseId()
   {
       return this.purchaseId;
   }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId()
   {
       return this.userId;
   }

	public void setApplicatedAt(String applicatedAt) {
        this.applicatedAt = applicatedAt;
    }
    public String getApplicatedAt()
   {
       return this.applicatedAt;
   }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getDeliveryDate()
   {
       return this.deliveryDate;
   }

    public void setIsDomestic(int isDomestic) {
        this.isDomestic = isDomestic;
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

    public void setCompany(int company) {
        this.company = company;
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

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }
   public String getMachineNo()
   {
       return this.machineNo;
   }

   public void setIsDelivered(int isDelivered) {
       this.isDelivered = isDelivered;
   }
  public int getIsDelivered()
  {
	  return this.isDelivered;
  }

   public int getEnable()
   {
       return this.isDeleted;
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

	public PurchaseCriteriaForm getCriteria()
   {
       return this.purchaseCriteria;
   }
	public void setCriteria(PurchaseCriteriaForm purchaseCriteria) {
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
		
		return DateUtils.SubtractDates(dateFrom, dateTo) <= DAYS_UNTIL_DELIVERY_DATE;
				
	}
}
