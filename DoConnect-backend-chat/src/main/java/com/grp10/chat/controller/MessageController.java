package com.grp10.chat.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grp10.chat.dto.MessageDto;
import com.grp10.chat.service.MessageService;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class MessageController {
	@Autowired
	private MessageService messageService;

	@PostMapping("/sendMessage")
	public MessageDto sendMessage(@Valid @RequestBody MessageDto messageDTO) {
		return messageService.sendMessage(messageDTO);
	}

	@GetMapping("/getMessage")
	public List<MessageDto> getMessage() {
		return messageService.getMessage();
	}
	
	@DeleteMapping("/deleteMessage")
	public void deleteMessage() {
	messageService.deleteMessage();
	}

}
