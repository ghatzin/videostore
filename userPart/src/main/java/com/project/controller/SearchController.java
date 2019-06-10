package com.project.controller;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.domain.Movie;
import com.project.domain.User;
import com.project.service.MovieService;
import com.project.service.UserService;

@Controller
public class SearchController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MovieService movieService;
	
	@RequestMapping("/searchMovie")
	public String searchMovie(@ModelAttribute("keyword") String keyword, Principal principal, Model model) {
		
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		List<Movie> movieList = movieService.keywordSearch(keyword);
		
		if(movieList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "movieBox";
		}
		
		model.addAttribute("movieList", movieList);
		
		return "movieBox";
	}
	
	@RequestMapping("/searchCategoryClick")
	public String searchCategoryClick(@PathParam("keyword") String keyword, Principal principal, Model model) {
		
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		List<Movie> movieList = movieService.keywordSearch(keyword);
		
		if(movieList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "movieBox";
		}
		
		model.addAttribute("movieList", movieList);
		
		return "movieBox";
	}

}










