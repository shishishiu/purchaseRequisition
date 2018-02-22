package com.osg.purchase.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.osg.purchase.customValidation.Password;
import com.osg.purchase.customValidation.PasswordEquals;

@PasswordEquals(message="{err.msg.password.not.equal}")
@Password(message="{err.msg.invalid.contrasena}")
public class PasswordEditForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private String userId;
	
		@NotNull
		@Size(min = 1, message="{err.msg.requerido}")
		private String passwordActual;
		
		@NotNull
		@Size(min = 1, message="{err.msg.requerido}")
		private String passwordNew;
		
		@NotNull
		@Size(min = 1, message="{err.msg.requerido}")
		private String passwordNewConfirm;
	  
		public String getUserId(){
			return this.userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getPasswordActual(){
			return this.passwordActual;
		}
		public void setPasswordActual(String passwordActual){
			this.passwordActual = passwordActual;
		}
		public String getPasswordNew(){
			return this.passwordNew;
		}
		public void setPasswordNew(String passwordNew){
			this.passwordNew = passwordNew;
		}
		public String getPasswordNewConfirm(){
			return this.passwordNewConfirm;
		}
		public void setPasswordNewConfirm(String passwordNewConfirm){
			this.passwordNewConfirm = passwordNewConfirm;
		}

}
