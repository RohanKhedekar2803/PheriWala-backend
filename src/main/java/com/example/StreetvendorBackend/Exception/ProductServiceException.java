package com.example.StreetvendorBackend.Exception;

import lombok.Data;

@Data
public class ProductServiceException extends RuntimeException {
	
	private String errorCode;
	
	public ProductServiceException(String message ,String errorcode) {
		super(message);
		this.errorCode=errorcode;
		
	}
}
