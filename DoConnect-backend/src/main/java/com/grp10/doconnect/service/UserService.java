package com.grp10.doconnect.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.grp10.doconnect.dto.AnswerDto;
import com.grp10.doconnect.dto.QuestionDto;
import com.grp10.doconnect.entity.Answer;
import com.grp10.doconnect.entity.ImageModel;
import com.grp10.doconnect.entity.Question;
import com.grp10.doconnect.entity.User;
import com.grp10.doconnect.vo.Message;

public interface UserService {
	public User userLogin(String email, String password);

	public String userLogout(Long userId);

	public User userRegister(@Valid User user);

	public Question askQuestion(@Valid QuestionDto askQuestionDTO);

	public Answer giveAnswer(@Valid AnswerDto postAnswerDTO);

	public List<Question> searchQuestion(String question);

	public List<Answer> getAnswers(Long questionId);

	public List<Question> getQuestions(String topic);

	public BodyBuilder uplaodImage(MultipartFile file) throws IOException;

	public ImageModel getImage(String imageName);

	public Message sendMessage(@Valid Message message);

}
