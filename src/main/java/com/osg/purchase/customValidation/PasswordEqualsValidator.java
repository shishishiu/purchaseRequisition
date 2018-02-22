package com.osg.purchase.customValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.osg.purchase.form.PasswordEditForm;


public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals,PasswordEditForm> {


	@Override
	public void initialize(PasswordEquals annotation) {
        
	}

	@Override
	public boolean isValid(PasswordEditForm value, ConstraintValidatorContext context) {
		
		String passwordNew = value.getPasswordNew();
		String passwordConfirm = value.getPasswordNewConfirm();
		
		if(!(passwordNew.equals("") && passwordConfirm.equals(""))) {
					
			if(passwordNew.equals(passwordConfirm)){
				return true;
			} else{
				return false;
			}
			
		}
		return true;
	}

}
