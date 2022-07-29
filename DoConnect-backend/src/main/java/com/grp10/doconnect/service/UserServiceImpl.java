package com.grp10.doconnect.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.grp10.doconnect.dto.AnswerDto;
import com.grp10.doconnect.dto.QuestionDto;
import com.grp10.doconnect.entity.Answer;
import com.grp10.doconnect.entity.ImageModel;
import com.grp10.doconnect.entity.Question;
import com.grp10.doconnect.entity.User;
import com.grp10.doconnect.exception.AlreadyTaken;
import com.grp10.doconnect.exception.ExceptionNotFound;
import com.grp10.doconnect.repository.AnswersRepository;
import com.grp10.doconnect.repository.ImageRepository;
import com.grp10.doconnect.repository.QuestionRepository;
import com.grp10.doconnect.repository.UserRepository;
import com.grp10.doconnect.vo.Message;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private AnswersRepository answerRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ImageRepository imageModelRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public User userLogin(String email, String password) {

		User user = userRepo.findByEmail(email);
		if (Objects.isNull(user))
			throw new ExceptionNotFound();

		if (user.getPassword().equals(password)) {
			user.setIsActive(true);
			userRepo.save(user);
		} else
			throw new ExceptionNotFound();
		return user;
	}

	@Override
	public String userLogout(Long userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ExceptionNotFound("User Not Found" + userId));
		user.setIsActive(false);
		userRepo.save(user);

		return "Logged Out";
	}

	@Override
	public User userRegister(User user) {

		User user1 = userRepo.findByEmail(user.getEmail());
		if (Objects.isNull(user1))
			return userRepo.save(user);

		throw new AlreadyTaken();
	}

	@Override
	public Question askQuestion(QuestionDto askQuestionDTO) {
		Question question = new Question();

		User user = userRepo.findById(askQuestionDTO.getUserId()).orElseThrow(() -> new ExceptionNotFound("User Not Found"));
		question.setQuestion(askQuestionDTO.getQuestion());
		question.setTopic(askQuestionDTO.getTopic());
		question.setUser(user);
		questionRepo.save(question);
		return question;
	}

	@Override
	public Answer giveAnswer(@Valid AnswerDto postAnswerDTO) {
		Answer answer = new Answer();
		User answerUser = userRepo.findById(postAnswerDTO.getUserId())
				.orElseThrow(() -> new ExceptionNotFound("User Not Found"));

		Question question = questionRepo.findById(postAnswerDTO.getQuestionId())
				.orElseThrow(() -> new ExceptionNotFound("Question Not Found"));
		answer.setQuestion(question);
		answer.setAnswer(postAnswerDTO.getAnswer());
		answer.setAnswerUser(answerUser);

		answerRepo.save(answer);
		return answer;
	}

	@Override
	public List<Question> searchQuestion(String question) {

		String sqlQuery = "from Question where (question like :question) and isApproved = 1";
		return entityManager.createQuery(sqlQuery, Question.class).setParameter("question", "%" + question + "%")
				.getResultList();
	}

	@Override
	public List<Answer> getAnswers(Long questionId) {
		return answerRepo.findByQuestionId(questionId);
	}

	@Override
	public List<Question> getQuestions(String topic) {
		if (topic.equalsIgnoreCase("All")) {
			return questionRepo.findByIsApprovedTrue();
		}
		return questionRepo.findByTopicAndApproved(topic);
	}

	@Override
	public BodyBuilder uplaodImage(MultipartFile file) throws IOException {
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		imageModelRepo.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}

	@Override
	public ImageModel getImage(String imageName) {
		final Optional<ImageModel> retrievedImage = imageModelRepo.findByName(imageName);
		ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}

	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

	@Override
	public Message sendMessage(@Valid Message message) {

		String url = "http://localhost:9595/chat/sendMessage";
		ResponseEntity<Message> responseEntity = restTemplate.postForEntity(url, message, Message.class);
		Message response = responseEntity.getBody();
		return response;
	}

}
