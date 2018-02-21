package com.osg.purchase.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.osg.purchase.customValidation.LoginIdUnique;
import com.osg.purchase.entity.DepartmentEntity;

@LoginIdUnique(message="{err.msg.loginid.unique}")
public class UserEditForm {

	@NotNull
    @Size(min = 1, max = 255, message="{err.msg.size}")
    private String userId;
	
	private int hidId;
	
	@NotNull
    @Size(min = 1, max = 255)
    private String username;

    private String password;

    @Size(min = 1, max = 255, message="{err.msg.size}")
    @Email
    private String email;

	@NotNull
	@Min(value=1, message="{err.msg.requerido}")
    private int departmentId;

    private String departmentname;

	private DepartmentEntity departmentEntity = new DepartmentEntity();

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getHidId() {
        return hidId;
    }
    public void setHidId(int hidId) {
        this.hidId = hidId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
	    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
	    
    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
    
    public DepartmentEntity getDepartment() {
    	return this.departmentEntity;
	}
    	 
	public void setDepartment(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}


}
