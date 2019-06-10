package com.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.domain.Movie;
import com.admin.repository.MovieRepository;
import com.admin.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	MovieRepository movieRepository;

	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	} 
	
	public List<Movie> findAll() {
		return (List<Movie>) movieRepository.findAll();
	}
	
	public Movie findOne(Long id) {
		return movieRepository.findById(id).get();
	}
	
	public void removeOne(Long id) {
		movieRepository.deleteById(id);
	}
}
