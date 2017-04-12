package org.handson.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.handson.data.Contact;
import org.handson.data.Person;
import org.handson.data.Person.Discriminator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private Person personToTest;
	private String personUnderTest;

	@Before
	public void setUp() throws Exception {
		personToTest = new Person();
		personToTest.setPersonName("Godwin");
		personToTest.setAddressLine1("Address line-1");
		personToTest.setAddressLine2("Address line-2");
		personToTest.setState("TN");
		personToTest.setZip("600048");
		personToTest.setCountry("INDIA");
		personToTest.setDiscriminator(Discriminator.MALE);
		personToTest.setLastUpdated(System.currentTimeMillis());
		Contact primaryContact = new Contact();
		primaryContact.setEMail("godwinjose@gmail.com");
		primaryContact.setPhoneNumber("+91-98998899898");

		Contact secondaryContact = new Contact();
		secondaryContact.setEMail("godwinjose1@gmail.com");
		secondaryContact.setPhoneNumber("+91-88998899898");

		List<Contact> contactDetails = new ArrayList<>();
		contactDetails.add(primaryContact);
		contactDetails.add(secondaryContact);

		personToTest.setContacts(contactDetails);
		ObjectMapper mapper = new ObjectMapper();
		personUnderTest = mapper.writeValueAsString(personToTest);
		log.warn("Person under test {} ", personUnderTest);
	}

	public void testAllResults() {

	}

	@Test
	public void testAdd() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/v1/persons").with(httpBasic("admin", "8688c3ad-f9f8-44af-a79f-3d252b2514e4"))
						.header("trackingId", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON_VALUE)
						.content(personUnderTest))
				.andExpect(status().isCreated()).andReturn();
		assertNotNull("Response cannot be null ", result.getResponse());
		assertNotNull("Response body cannot be null ", result.getResponse().getContentAsString());
	}

}
