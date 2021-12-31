package com.bridgelabz.bookstore.user_registration.dto;

import lombok.Data;

public @Data class ResponseDto {
	   private String message;
	   private Object data;
	   
	   public ResponseDto( String message , Object data) {
		this.message = message;
		this.data = data;	
	} 
	   public ResponseDto(String message) {
		   this.message = message;
	   }
}
