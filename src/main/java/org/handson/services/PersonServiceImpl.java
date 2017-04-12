package org.handson.services;

import org.handson.data.Person;
import org.handson.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person findOne(Long personId) {
		// TODO Auto-generated method stub
		return personRepository.findOne(personId);
	}
	
	@Override
	public Person save(Person person) {
		// TODO Auto-generated method stub
		return personRepository.save(person);
	}

}
