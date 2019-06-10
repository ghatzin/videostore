package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Message;
import com.project.repository.MessageRepository;
import com.project.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	public Message save(Message message) {
		return messageRepository.save(message);
	}

	public List<Message> findAllByUserId(Long id) {
		return messageRepository.findAllByUserId(id);
	}
}
