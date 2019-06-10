package com.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {

	List<Movie> findByTitleContaining(String keyword);

	List<Movie> findByStudioContaining(String keyword);

	List<Movie> findByDirectorContaining(String keyword);

	List<Movie> findByGenreContaining(String keyword);

	List<Movie> findByLanguageContaining(String keyword);

}
