package org.handson.common.exception.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9178891186876427816L;
	private String personId;

	public PersonNotFoundException(String personId) {
		super(String.format("Person with id %s not found ", personId));
		log.error("Person with id {}  not found", personId);
	}
}
