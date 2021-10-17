package com.devsuperior.movieflix.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieGenreDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		Optional<Movie> obj = movieRepository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado!"));
		return new MovieDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> findReviewByMovieId(Long id) {
		List<Review> list = reviewRepository.find(id);
		return list.stream().map(x -> new ReviewDTO(x, x.getUser())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<MovieGenreDTO> findAllPaged(Long genreId, Pageable pageable) {
		List<Genre> genre = (genreId == 0) ? null : Arrays.asList(genreRepository.getOne(genreId));
		Page<Movie> list = movieRepository.find(genre, pageable);
		return list.map(x -> new MovieGenreDTO(x));
	}

}
