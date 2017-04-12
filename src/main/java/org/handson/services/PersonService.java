package org.handson.services;

import org.handson.data.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
	Person findOne(Long personId);
	Person save(Person person);
}
