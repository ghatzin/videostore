package com.project.service;

import java.util.List;

import com.project.domain.Message;

public interface MessageService {

	Message save(Message message);

	List<Message> findAllByUserId(Long id);
}
