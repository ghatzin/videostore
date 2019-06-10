package com.admin.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.admin.domain.Movie;
import com.admin.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addMovieGet(Model model) {
		Movie movie = new Movie();
		model.addAttribute("movie", movie);
		return "addMovie";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addMoviePost(@ModelAttribute("movie") Movie movie, HttpServletRequest request) {
		
		movieService.save(movie);
		MultipartFile movieImage = movie.getMovieImage();
		
		try {
			byte[] bytes = movieImage.getBytes();
			String name = movie.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/movie/" + name)));
			stream.write(bytes);
			stream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:movieList";
	}
	
	
	@RequestMapping("/movieList")
	public String movieList(Model model) {
		List<Movie> movieList = movieService.findAll();
		model.addAttribute("movieList", movieList);
		return "movieList";
	}
	
	@RequestMapping("/movieInfo")
	public String movieInfo(@RequestParam("id") Long id, Model model) {
		Movie movie = movieService.findOne(id);
		model.addAttribute("movie", movie);
		return "movieInfo";
	}
	
	@RequestMapping(value="/updateMovie", method=RequestMethod.GET)
	public String updateMovie(@RequestParam("id") Long id, Model model) {
		
		Movie movie = movieService.findOne(id);
		model.addAttribute("movie", movie);
		return "updateMovie";
	}
	
	@RequestMapping(value="/updateMovie", method=RequestMethod.POST)
	public String updateMoviePost(@ModelAttribute("movie") Movie movie, HttpServletRequest request) {
		
		movieService.save(movie);
		
		MultipartFile movieImage = movie.getMovieImage();
		
		if(!movieImage.isEmpty()) {
			try {
				byte[] bytes = movieImage.getBytes();
				String name = movie.getId() + ".png";
				Files.delete(Paths.get("src/main/resources/static/image/movie/" + name));            
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/movie/" + name)));
				stream.write(bytes);
				stream.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/movie/movieInfo?id=" + movie.getId();
	}
	
	@RequestMapping("/deleteMovie")
	public String deleteMovie(@ModelAttribute("id") Long id, Model model) {
		movieService.removeOne(id);
		List<Movie> movieList = movieService.findAll();
		model.addAttribute("movieList", movieList);
		
		return "redirect:/movie/movieList";
		
	}

}













