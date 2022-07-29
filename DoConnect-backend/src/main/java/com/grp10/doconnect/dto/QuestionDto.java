package com.grp10.doconnect.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
	@NotNull(message = "please give the id")
	private Long userId;
	@NotBlank(message = "Question is required")
	private String question;
	@NotBlank(message = "please provide the topic")
	private String topic;

}
