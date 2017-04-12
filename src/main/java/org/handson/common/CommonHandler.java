package org.handson.common;

import java.util.ArrayList;
import java.util.List;

import org.handson.common.exception.RequestValidationException;
import org.handson.common.exception.resource.ErrorResource;
import org.handson.common.exception.resource.PersonNotFoundException;
import org.handson.common.exception.resource.RequestFieldErrorsResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(RequestValidationException.class)
	public ErrorResource handleValidationError(RuntimeException exception, WebRequest request) {
		log.error("Request Validation Exception {} ", exception.getMessage());
		List<RequestFieldErrorsResource> fieldErrorResources = new ArrayList<>();

		RequestValidationException rve = (RequestValidationException) exception;
		List<FieldError> fieldErrors = rve.getErrors().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			RequestFieldErrorsResource fieldErrorResource = new RequestFieldErrorsResource(fieldError.getObjectName(),
					fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
			fieldErrorResources.add(fieldErrorResource);
		}
		ErrorResource error = new ErrorResource("01", exception.getMessage(), fieldErrorResources);
		return error;
	}

	@ExceptionHandler(PersonNotFoundException.class)
	public ErrorResource handlePersonNotFound(RuntimeException exception, WebRequest request) {
		log.error(exception.getMessage());
		ErrorResource error = new ErrorResource("02", exception.getMessage());
		return error;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResource handleGenericException(RuntimeException exception, WebRequest request) {
		log.error(exception.getMessage());
		ErrorResource error = new ErrorResource("03", exception.getMessage());
		return error;
	}

}
