package com.fang.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidator {
	
	private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	
	public static<T> Map<String, String> validate(Object object){
		Validator validator = validatorFactory.getValidator();
		Set result = validator.validate(object);
		if(result.isEmpty()) {
			return new LinkedHashMap<>();
		}else {
			LinkedHashMap<String, String> errors = new LinkedHashMap<>();
			Iterator iterator = result.iterator();
			while(iterator.hasNext()) {
				ConstraintViolation violation = (ConstraintViolation)iterator.next();
				errors.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			return errors;
		}
	}

}
