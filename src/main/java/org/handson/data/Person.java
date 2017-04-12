package org.handson.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	// @NotNull(message = "Person id is mandatory")
	private Long personId;
	@NotNull(message = "Person name is mandatory")
	private String personName;
	private Discriminator discriminator;
	private String addressLine1;
	private String addressLine2;
	private String state;
	private String country;
	private String zip;
	private Long lastUpdated;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY, targetEntity = Contact.class)
	private List<Contact> contacts;

	public enum Discriminator {
		MALE, FEMALE, NA;
		public static final Discriminator from(String discriminator) {
			if (!Strings.isNullOrEmpty(discriminator) && discriminator.equalsIgnoreCase("Male")) {
				return MALE;
			} else if (!Strings.isNullOrEmpty(discriminator) && discriminator.equalsIgnoreCase("Female")) {
				return FEMALE;
			}

			else if (!Strings.isNullOrEmpty(discriminator) && discriminator.equalsIgnoreCase("NA")) {
				return NA;
			} else {
				throw new IllegalArgumentException("Gender is an enumeration of values Male , Female or N.A");
			}
		}
	}
}