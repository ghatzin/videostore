package com.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Movie;
import com.project.repository.MovieRepository;
import com.project.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService { 
	
	@Autowired 
	private MovieRepository movieRepository;
	
	public List<Movie> findAll() {
		return (List<Movie>) movieRepository.findAll();
	}

	public Movie findOne(Long id) {
		return movieRepository.findById(id).get();
	}
	
	public List<Movie> keywordSearch(String keyword) {
		
		List<Movie> movieListByTitle = movieRepository.findByTitleContaining(keyword);
		List<Movie> movieListByStudio = movieRepository.findByStudioContaining(keyword);
		List<Movie> movieListByDirector = movieRepository.findByDirectorContaining(keyword);
		List<Movie> movieListByGenre = movieRepository.findByGenreContaining(keyword);
		List<Movie> movieListByLanguage = movieRepository.findByLanguageContaining(keyword);
 		
		List<Movie> activeMovieList = new ArrayList<>();
		
		for(Movie element : movieListByTitle) {
			if(element.isActive()) {
				activeMovieList.add(element);
			}
		}	
		
		for(Movie element : movieListByStudio) {
			if(element.isActive() && !activeMovieList.contains(element)) {
				activeMovieList.add(element);
				}	
		}
		
		for(Movie element : movieListByDirector) {
			if(element.isActive() && !activeMovieList.contains(element)) {
				activeMovieList.add(element);
			}
		}	
		
		for(Movie element : movieListByGenre) {
			if(element.isActive() && !activeMovieList.contains(element)) {
				activeMovieList.add(element);
			}
		}	
		
		for(Movie element : movieListByLanguage) {
			if(element.isActive() && !activeMovieList.contains(element)) {
				activeMovieList.add(element);
			}
		}	
		
		return activeMovieList;
	}
	
}
