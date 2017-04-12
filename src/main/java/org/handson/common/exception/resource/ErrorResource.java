package org.handson.common.exception.resource;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource {
	private String code;
	private String message;
	private List<RequestFieldErrorsResource> requestFieldResources;
	public ErrorResource(String code , String message){
		
	}
}
