package com.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.admin.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long >{

}
