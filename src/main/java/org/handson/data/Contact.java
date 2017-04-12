package org.handson.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(value=JsonInclude.Include.NON_NULL)

public class Contact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2604144797516773533L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long contactId;
	private String eMail;
	private String phoneNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId")
	private Person person;
	
	@JsonIgnore
	private Person getPerson(){
		return this.person;
	}

}
