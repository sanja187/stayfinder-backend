package com.stayfinder.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.stayfinder.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByHostelId(Long hostelId);
}