package com.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.domain.Message;
import com.admin.repository.MessageRepository;
import com.admin.service.MessageService;

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
	
	public List<Message> findAll() {
		return (List<Message>) messageRepository.findAll();
	}
	
	public Message findOne(Long id) {
		return messageRepository.findById(id).get();
	}
}
