package org.handson.controller;

import javax.validation.Valid;

import org.handson.common.DiscriminatorBinder;
import org.handson.common.RestCommonValidator;
import org.handson.common.exception.RequestValidationException;
import org.handson.data.Person;
import org.handson.data.Person.Discriminator;
import org.handson.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/v1/persons")
class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(method = RequestMethod.GET, path = "/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person getPerson(@RequestHeader(name = "trackingId") String trackingId, @Valid @PathVariable Long personId,
			BindingResult bindingResult) {
		if (bindingResult != null && bindingResult.hasErrors()) {
			log.warn("Mandatory paramters missing in request with tracking id {} ", trackingId);
		}
		Person person = RestCommonValidator.checkFound(personService.findOne(personId), personId.toString());
		log.error("Retrieved person for person id {} ,  {} ", personId, person);
		return person;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person addPerson(@RequestHeader(name = "trackingId") String trackingId, @Valid @RequestBody Person person,
			BindingResult bindingResult) {
		log.warn("Person passed in and parsed is {} " , person);
		if (bindingResult != null && bindingResult.hasErrors()) {
			log.warn("Mandatory paramters missing in request with tracking id {} ", trackingId);
			throw requestValidationException("Error parsing request , validation errors have occured", bindingResult);
		}
		log.debug("Create a person {} ", person);
		return personService.save(person);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person updatePerson(@RequestHeader(name = "trackingId") String trackingId, @PathVariable Long personId,
			@Valid @RequestBody Person person, BindingResult bindingResult) {

		if (bindingResult != null && bindingResult.hasErrors()) {
			log.warn("Mandatory paramters missing in request with tracking id {} ", trackingId);
			throw requestValidationException("Error parsing request , validation errors have occured", bindingResult);
		}
		log.debug("Update a person {} ", person);
		RestCommonValidator.checkFound(personService.findOne(personId), personId.toString());
		return personService.save(person);
	}

	private RequestValidationException requestValidationException(String message, BindingResult bindingResult) {
		return new RequestValidationException(message, bindingResult);
	}

	@InitBinder
	void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Discriminator.class, new DiscriminatorBinder());
	}
}