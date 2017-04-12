package org.handson.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(code=HttpStatus.UNPROCESSABLE_ENTITY )
public class RequestValidationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Errors errors;
	
	public RequestValidationException(String message , Errors errors){
		super(message);
		log.error("Validation exception processing request {} " , message);
		this.errors = errors;
	}
}
