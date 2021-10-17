package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM TB_REVIEW WHERE Movie_id = :id")
	List<Review> find(Long id);
}
