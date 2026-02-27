package com.stayfinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stayfinder.model.HostelReview;
import com.stayfinder.model.User;
import com.stayfinder.repository.HostelReviewRepository;
import com.stayfinder.repository.UserRepository;
import com.stayfinder.security.JwtUtil;

@Service
public class HostelReviewService {

    @Autowired
    private HostelReviewRepository reviewRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    public HostelReview addReview(String token, Long hostelId, int rating, String comment){

        token = token.substring(7);
        Long studentId = jwtUtil.extractUserId(token);

        User student = userRepo.findById(studentId).orElse(null);

        HostelReview review = new HostelReview();
        review.setHostelId(hostelId);
        review.setStudentId(studentId);
        review.setRating(rating);
        review.setComment(comment);
        review.setStudentName(student.getName());

        return reviewRepo.save(review);
    }

    public List<HostelReview> getReviews(Long hostelId){
        return reviewRepo.findByHostelId(hostelId);
    }

    public double getAverageRating(Long hostelId){

        List<HostelReview> list = reviewRepo.findByHostelId(hostelId);

        if(list.isEmpty()) return 0;

        int sum = 0;

        for(HostelReview r : list){
            sum += r.getRating();
        }

        return (double) sum / list.size();
    }
}
