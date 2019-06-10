package com.admin.service;

import java.util.List;

import com.admin.domain.Movie;

public interface MovieService {
	
	Movie save(Movie movie);
	
	List<Movie> findAll();

	Movie findOne(Long id);

	void removeOne(Long id);

}
