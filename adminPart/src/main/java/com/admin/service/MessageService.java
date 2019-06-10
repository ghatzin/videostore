package com.admin.service;

import java.util.List;

import com.admin.domain.Message;

public interface MessageService {

	Message save(Message message);

	List<Message> findAllByUserId(Long id);

	List<Message> findAll();

	Message findOne(Long id);
}
