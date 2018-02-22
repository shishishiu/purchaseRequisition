package com.osg.purchase.customValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.form.PasswordEditForm;
import com.osg.purchase.repository.MemberRepository;


public class PasswordValidator implements ConstraintValidator<Password, PasswordEditForm> {

	@Autowired
	MemberRepository memberRepository;

	@Override
	public void initialize(Password annotation) {
   	}

	@Override
	public boolean isValid(PasswordEditForm value, ConstraintValidatorContext context) {
		
		String password = value.getPasswordActual();
		String userId = value.getUserId();
		
		if(password!=null && !password.equals("")) {
			
			MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(userId, 0);
			
    		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    		
    		
    		if(bcrypt.matches(password, entity.getPassword())) {
    			
    			return true;
    			
    		}else return false;
    		

			
		}
		
		return true;
	}

}
