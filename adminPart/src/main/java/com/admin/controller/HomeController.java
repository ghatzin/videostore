package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.domain.Message;
import com.admin.domain.User;
import com.admin.service.MessageService;
import com.admin.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	@RequestMapping("/home")
	public String home() {
		return "home";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/home";
	}
	
	@RequestMapping("/messages")
	public String checkMessage(Model model) {
		
		List<Message> messageList = messageService.findAll();
		
		model.addAttribute("messageList", messageList);
		
		return "messages";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply(@RequestParam("id") Long id, Model model) {
		
		Message message = messageService.findOne(id);
		User user = message.getUser();
		model.addAttribute("message", message);
		model.addAttribute("user", user);
		
		return "reply";
	}
	
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String newMessagePost(@ModelAttribute("message") Message message, @ModelAttribute("id") Long id,
			@ModelAttribute("userMessage") String userMessage, @ModelAttribute("adminReply") String adminReply,
			@ModelAttribute("userId") Long userId) {
		
		User user = userService.findById(userId);
		
		message.setUser(user);
		
		messageService.save(message);
		
		return "home";
	}
	
}