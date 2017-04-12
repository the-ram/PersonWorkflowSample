package org.handson.common.exception.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestFieldErrorsResource {
	private String resource;
	private String field;
	private String code;
	private String message;	
}
