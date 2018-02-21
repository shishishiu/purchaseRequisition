package com.osg.purchase.customValidation;

import javax.validation.*;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.repository.MemberRepository;

public class LoginIdValidator implements ConstraintValidator<LoginIdUnique,Object> {

	@Autowired
	MemberRepository memberRepository;

	private String hidId;
	private String userId;
	
	@Override
	public void initialize(LoginIdUnique annotation) {
		this.hidId = annotation.fieldHidId();
        this.userId = annotation.fieldUserId();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		int hidId = (int)beanWrapper.getPropertyValue(this.hidId);
		String userId = (String)beanWrapper.getPropertyValue(this.userId);

		MemberEntity entity = memberRepository.findByUserId(userId);
		

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
