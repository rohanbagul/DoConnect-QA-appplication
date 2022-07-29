package com.grp10.doconnect.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AlreadyTaken extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String errorMsg;

}
