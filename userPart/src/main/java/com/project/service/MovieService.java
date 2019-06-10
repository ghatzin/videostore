package com.project.service;

import java.util.List;

import com.project.domain.Movie;

public interface MovieService {
	
	List<Movie> findAll();

	Movie findOne(Long id);

	List<Movie> keywordSearch(String keyword);
	
}
