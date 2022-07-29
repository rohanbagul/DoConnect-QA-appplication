package com.grp10.chat.service;

import java.util.List;

import javax.validation.Valid;

import com.grp10.chat.dto.MessageDto;

public interface MessageService {
	public MessageDto sendMessage(@Valid MessageDto messageDTO);

	public List<MessageDto> getMessage();

	public void deleteMessage();

}
