package com.grp10.doconnect.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
	@NotNull(message = "please provide the user Id")
	private Long userId;
	@NotNull(message = "please provide the Question Id")
	private Long questionId;
	@NotBlank(message = "Answer is required")
	private String answer;
}
