package com.grp10.doconnect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@org.springframework.web.bind.annotation.ExceptionHandler(value = ExceptionNotFound.class)
	public ResponseEntity<Object> exception(ExceptionNotFound exception) {
		return new ResponseEntity<>("Invalid ID ---> Not found", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = AlreadyTaken.class)
	public ResponseEntity<Object> exception2(AlreadyTaken exception) {
		return new ResponseEntity<>("Already There --> Must be unique", HttpStatus.BAD_REQUEST);
	}

}
