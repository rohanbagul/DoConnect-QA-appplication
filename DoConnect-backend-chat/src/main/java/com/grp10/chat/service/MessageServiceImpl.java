package com.grp10.chat.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grp10.chat.dto.MessageDto;
import com.grp10.chat.entity.Message;
import com.grp10.chat.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	private MessageRepository messageRepo;

	@Override
	public MessageDto sendMessage(@Valid MessageDto messageDTO) {

		Message message = new Message();
		message.setMessage(messageDTO.getMessage());
		message.setFromUser(messageDTO.getFromUser());
		message = messageRepo.save(message);

		messageDTO.setFromUser(message.getFromUser());
		messageDTO.setMessage(message.getMessage());

		return messageDTO;
	}

	@Override
	public List<MessageDto> getMessage() {
		List<MessageDto> data = new ArrayList<MessageDto>();

		List<Message> messages = messageRepo.findAll();
		for (Message message : messages) {

			MessageDto messageDTO = new MessageDto();
			messageDTO.setFromUser(message.getFromUser());
			messageDTO.setMessage(message.getMessage());
			data.add(messageDTO);
		}

		return data;
	}

	@Override
	public void deleteMessage() {
		messageRepo.deleteAll();
		
	}

}
