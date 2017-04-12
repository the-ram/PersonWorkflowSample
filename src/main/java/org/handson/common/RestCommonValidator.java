package org.handson.common;

import org.handson.common.exception.resource.PersonNotFoundException;

public class RestCommonValidator {
	public static <T> T checkFound(final T resource,final String id){
		if(resource == null){
			throw new PersonNotFoundException(id);
		}
		return resource;	
	}
}
