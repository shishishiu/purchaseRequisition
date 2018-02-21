package com.osg.purchase.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "master_departments")
@Table(name = "master_departments")
public class DepartmentEntity {

	@OneToMany(mappedBy="departmentEntity")
	private List<MemberEntity> userList;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = false)
    private String departmentId;

    @Column(nullable = false, unique = false)
    private String departmentName;

    @Column(nullable = false)
    private int isDeleted;

    private int order;
    
    public int getId()
   {
       return this.id;
   }

    public String getDepartmentId()
   {
       return this.departmentId;
   }

    public String getDepartmentName()
   {
       return this.departmentName;
   }

    public void setOrder(int order) {
        this.order = order;
    }
    public int getOrder()
   {
       return this.order;
   }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
    public int getIsDeleted()
   {
       return this.isDeleted;
   }

    public List<MemberEntity> getUserList() {
    	return this.userList;
	}

}
