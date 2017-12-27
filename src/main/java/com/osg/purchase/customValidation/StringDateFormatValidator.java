package com.osg.purchase.customValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.osg.purchase.util.DateUtils;

public class StringDateFormatValidator implements ConstraintValidator<StringDateFormat,String> {

	@Override
	public void initialize(StringDateFormat arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String fieldVal, ConstraintValidatorContext arg1) {
		
		return DateUtils.IsDate(fieldVal, "dd-MM-yyyy");
		
	}

}
