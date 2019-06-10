package com.project.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.domain.Movie;
import com.project.domain.Purchase;
import com.project.domain.User;
import com.project.service.MovieService;
import com.project.service.PurchaseService;
import com.project.service.UserService;

@Controller
public class PurchaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private PurchaseService purchaseService;

	@RequestMapping("/completeOrder")
	public String completeOrder(@ModelAttribute("id") Long id, Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		Movie movie = movieService.findOne(id);
		
		Purchase purchase = new Purchase();
		purchase.setMovie(movie);
		purchase.setUser(user);
		Date date = new Date();
		purchase.setPurchaseDate(date);
		purchase.setTotal(movie.getPrice());
		
		purchaseService.save(purchase);
		model.addAttribute("purchaseSuccess", true);
		model.addAttribute("movie", movie);
		model.addAttribute("user", user);
		model.addAttribute("purchase", purchase);
		
		return "success";
	}
	
	@RequestMapping("/purchaseMovie")
	public String purchaseMovie(@ModelAttribute("id") Long id, Model model, Principal principal) {
		
		Movie movie = movieService.findOne(id);
		User user = userService.findByUsername(principal.getName()); 
		
		if(!movie.isActive()) {
			model.addAttribute("inactiveMovie", true);
			return "forward:/movieDetails?id=" + movie.getId();
		}
		
		List<Purchase> purchasedMovies = purchaseService.findAllByUserId(user.getId());
		
		if(!purchasedMovies.isEmpty()) {
			for(Purchase element: purchasedMovies) {
				if(element.getMovie().getTitle().equalsIgnoreCase(movie.getTitle())) {
					model.addAttribute("movie", movie);
					model.addAttribute("user", user);
					model.addAttribute("alreadyPurchased", true);
					return "movieDetails";
				}
			}
		}
		
		model.addAttribute("movie", movie);
		model.addAttribute("user", user);
		
		return "checkOut";	
	}
	
}
