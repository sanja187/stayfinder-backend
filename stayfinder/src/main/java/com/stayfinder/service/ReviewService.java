package com.stayfinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stayfinder.model.Review;
import com.stayfinder.repository.ReviewRepository;
import com.stayfinder.security.JwtUtil;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Review addReview(String token, Long hostelId, int rating, String comment){

        token = token.substring(7);
        Long userId = jwtUtil.extractUserId(token);

        Review r = new Review();
        r.setHostelId(hostelId);
        r.setUserId(userId);
        r.setRating(rating);
        r.setComment(comment);

        return reviewRepository.save(r);
    }

    public List<Review> getReviews(Long hostelId){
        return reviewRepository.findByHostelId(hostelId);
    }
}