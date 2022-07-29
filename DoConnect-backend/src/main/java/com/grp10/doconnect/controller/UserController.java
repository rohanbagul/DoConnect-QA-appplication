package com.grp10.doconnect.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grp10.doconnect.dto.AnswerDto;
import com.grp10.doconnect.dto.QuestionDto;
import com.grp10.doconnect.entity.Answer;
import com.grp10.doconnect.entity.ImageModel;
import com.grp10.doconnect.entity.Question;
import com.grp10.doconnect.entity.User;
import com.grp10.doconnect.service.UserService;
import com.grp10.doconnect.vo.Message;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login/{email}/{password}")
	public User userLogin(@PathVariable String email, @PathVariable String password) {
		return userService.userLogin(email, password);
	}

	@GetMapping("/logout/{userId}")
	public String userLogout(@PathVariable Long userId) {
		return userService.userLogout(userId);
	}

	@PostMapping("/register")
	public User userRegister(@Valid @RequestBody User user) {
		return userService.userRegister(user);
	}

	@PostMapping("/askQuestion")
	public Question askQuestion(@Valid @RequestBody QuestionDto askQuestionDTO) {
		return userService.askQuestion(askQuestionDTO);
	}

	@PostMapping("/giveAnswer")
	public Answer giveAnswer(@Valid @RequestBody AnswerDto postAnswerDTO) {
		return userService.giveAnswer(postAnswerDTO);
	}

	@GetMapping("/searchQuestion/{question}")
	public List<Question> searchQuestion(@PathVariable String question) {
		return userService.searchQuestion(question);
	}

	@GetMapping("/getAnswers/{questionId}")
	public List<Answer> getAnswers(@PathVariable Long questionId) {
		return userService.getAnswers(questionId);
	}

	@GetMapping("/getQuestions/{topic}")
	public List<Question> getQuestions(@PathVariable String topic) {
		return userService.getQuestions(topic);
	}

	@PostMapping("/upload")
	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		return userService.uplaodImage(file);
	}

	@GetMapping(path = { "/get/{imageName}" })
	public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
		return userService.getImage(imageName);
	}
	
	@PostMapping("/sendMessage")
	public Message sendMessage(@Valid @RequestBody Message message) {
		return userService.sendMessage(message);
	}

}
