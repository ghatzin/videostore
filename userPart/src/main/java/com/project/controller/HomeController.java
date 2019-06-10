package com.project.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.domain.Message;
import com.project.domain.Movie;
import com.project.domain.Purchase;
import com.project.domain.User;
import com.project.domain.security.PasswordResetToken;
import com.project.domain.security.Role;
import com.project.domain.security.UserRole;
import com.project.service.MessageService;
import com.project.service.MovieService;
import com.project.service.PurchaseService;
import com.project.service.UserService;
import com.project.service.impl.UserSecurityService;
import com.project.utility.MailConstructor;
import com.project.utility.SecurityUtility;


@Controller
public class HomeController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping(value="/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception{
		
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			
			return "myAccount";
		}
		
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			
			return "myAccount";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
		
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
		
		return "myAccount";
	}
	

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}
	
	
	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(
			@ModelAttribute("user") User user,@ModelAttribute("newPassword") String newPassword,@ModelAttribute("firstName") String firstName,
			@ModelAttribute("lastName") String lastName,@ModelAttribute("phone") String phone,Model model
			) throws Exception {
		User currentUser = userService.findById(user.getId());
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		
		if (userService.findByEmail(user.getEmail())!=null) {
			if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		
		
		if (userService.findByUsername(user.getUsername())!=null) {
			if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}
		
		
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		
		currentUser.setPassword(passwordEncoder.encode(newPassword));
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setPhone(phone);
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		
		userService.save(currentUser);
		
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "myProfile";
	}
	
	@RequestMapping("/movieBox")
	public String movieBox(Model model, Principal principal) {
		
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user",user);
		}
		List<Movie> movieList = movieService.findAll();
		model.addAttribute("movieList", movieList);
		
		
		return "movieBox";
	}
	
	@RequestMapping("/movieDetails")
	public String movieDetails(@PathParam("id") Long id, Model model, Principal principal) {
		
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user",user);
		}
		
		Movie movie = movieService.findOne(id);
		
		model.addAttribute("movie", movie);
		
		return "movieDetails";
	}
	
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("classActiveEdit", true);
		
		List<Purchase> purchasedMovies = purchaseService.findAllByUserId(user.getId());
		
		model.addAttribute("purchasedMovies", purchasedMovies);
		
		List<Message> messageList = messageService.findAllByUserId(user.getId());
		
		model.addAttribute("messageList", messageList);
		
		return "myProfile";
	}

	
	@RequestMapping("/newMessage")
	public String newMessage(Model model, Principal principal) {
		
		Message message = new Message();
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("message", message);
		
		return "newMessage";
	}
	
	@RequestMapping(value="/newMessage", method=RequestMethod.POST)
	public String newMessagePost(@ModelAttribute("userMessage") String userMessage, HttpServletRequest request, Principal principal) {
		
		Message message = new Message();
		User user = userService.findByUsername(principal.getName());
		
		message.setUser(user);
		message.setUserMessage(userMessage);
		messageService.save(message);
		
		return "redirect:myProfile";
	}
	
}















