package com.grp10.doconnect.service;
import java.util.List;

import com.grp10.doconnect.dto.ResponseDto;
import com.grp10.doconnect.entity.Admin;
import com.grp10.doconnect.entity.Answer;
import com.grp10.doconnect.entity.Question;
import com.grp10.doconnect.entity.User;

public interface AdminService {
	public Admin adminLogin(String email, String password);

	public String adminLogout(Long adminId);

	public Admin adminRegister(Admin admin);

	public List<Question> getUnApprovedQuestions();

	public List<Answer> getUnApprovedAnswers();

	public Question approveQuestion(Long questionId);

	public Answer approveAnswer(Long answerId);

	public ResponseDto deleteQuestion(Long questionId);

	public ResponseDto deleteAnswer(Long answerId);

	public User getUser(String email);

	public List<User> getAllUser();

}
