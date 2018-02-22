package com.osg.purchase.customValidation;

import javax.validation.*;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.form.UserEditForm;
import com.osg.purchase.repository.MemberRepository;

public class LoginIdValidator implements ConstraintValidator<LoginIdUnique,UserEditForm> {

	@Autowired
	MemberRepository memberRepository;

	private String hidId;
	private String userId;
	
	@Override
	public void initialize(LoginIdUnique annotation) {
		this.hidId = annotation.fieldId();
        this.userId = annotation.fieldUserId();
	}

	@Override
	public boolean isValid(UserEditForm value, ConstraintValidatorContext context) {
		
		int hidId = value.getId();
		String userId = value.getUserId();

		MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(userId,0);
		

		if(entity == null){
			return true;
		} else{
			
			if(hidId > 0 && hidId == entity.getId()){
				return true;
			}
			
			return false;
		}
	}

}
