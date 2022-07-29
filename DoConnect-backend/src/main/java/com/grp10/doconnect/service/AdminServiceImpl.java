package com.grp10.doconnect.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grp10.doconnect.dto.ResponseDto;
import com.grp10.doconnect.entity.Admin;
import com.grp10.doconnect.entity.Answer;
import com.grp10.doconnect.entity.Question;
import com.grp10.doconnect.entity.User;
import com.grp10.doconnect.exception.AlreadyTaken;
import com.grp10.doconnect.exception.ExceptionNotFound;
import com.grp10.doconnect.repository.AdminRepository;
import com.grp10.doconnect.repository.AnswersRepository;
import com.grp10.doconnect.repository.QuestionRepository;
import com.grp10.doconnect.repository.UserRepository;
import com.grp10.doconnect.util.EmailResponse;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private AnswersRepository answerRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailResponse emailSenderService;

	@Override
	public Admin adminLogin(String email, String password) {

		Admin admin = adminRepo.findByEmail(email);
		if (Objects.isNull(admin))
			throw new ExceptionNotFound();

		if (admin.getPassword().equals(password)) {
			admin.setIsActive(true);
			adminRepo.save(admin);
		} else
			throw new ExceptionNotFound();
		return admin;
	}

	@Override
	public String adminLogout(Long adminId) {

		Admin admin = adminRepo.findById(adminId).orElseThrow(() -> new ExceptionNotFound("Admin not found"));
		admin.setIsActive(false);
		adminRepo.save(admin);
		return "Logged Out";
	}

	@Override
	public Admin adminRegister(Admin admin) {

		Admin admin1 = adminRepo.findByEmail(admin.getEmail());
		if (Objects.isNull(admin1))
			return adminRepo.save(admin);

		throw new AlreadyTaken();
	}

	@Override
	public List<Question> getUnApprovedQuestions() {
		return questionRepo.findByIsApproved();
	}

	@Override
	public List<Answer> getUnApprovedAnswers() {
		return answerRepo.findByIsApproved();
	}

	@Override
	public Question approveQuestion(Long questionId) {

		Question question = questionRepo.findById(questionId).orElseThrow(() -> new ExceptionNotFound("Question not found"));

		question.setIsApproved(true);
		question = questionRepo.save(question);
		return question;
	}

	@Override
	public Answer approveAnswer(Long answerId) {
		Answer answer = answerRepo.findById(answerId).orElseThrow(() -> new ExceptionNotFound("Answer not found"));

		answer.setIsApproved(true);
		answer = answerRepo.save(answer);

		return answer;
	}
	
	@Override
	public ResponseDto deleteQuestion(Long questionId) {
		ResponseDto responseDTO = new ResponseDto();
		Question question = questionRepo.findById(questionId).orElseThrow(() -> new ExceptionNotFound("Question not found"));

		questionRepo.delete(question);
		responseDTO.setMsg("Question removed");
		return responseDTO;
	}

	@Override
	public ResponseDto deleteAnswer(Long answerId) {
		ResponseDto responseDTO = new ResponseDto();

		Answer answer = answerRepo.findById(answerId).orElseThrow(() -> new ExceptionNotFound("Answer not found"));

		answerRepo.delete(answer);
		responseDTO.setMsg("Answer Removed");
		return responseDTO;
	}

	public Boolean sendMail(String emailId, String type) {
		try {
			emailSenderService.sendEmail(emailId, type, type);
			return true;
		} catch (Exception e) {
			System.out.println("error in sending mail " + e);
			return false;
		}
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}
}
