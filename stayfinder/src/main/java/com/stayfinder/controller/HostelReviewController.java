package com.stayfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stayfinder.model.HostelReview;
import com.stayfinder.service.HostelReviewService;

@RestController
@RequestMapping("/review")
public class HostelReviewController {

    @Autowired
    private HostelReviewService reviewService;

    @PostMapping("/add")
    public HostelReview addReview(
        @RequestHeader("Authorization") String token,
        @RequestParam Long hostelId,
        @RequestParam int rating,
        @RequestParam String comment
    ){
        return reviewService.addReview(token, hostelId, rating, comment);
    }

    @GetMapping("/hostel/{id}")
    public List<HostelReview> getReviews(@PathVariable Long id){
        return reviewService.getReviews(id);
    }

    @GetMapping("/avg/{id}")
    public double getAverage(@PathVariable Long id){
        return reviewService.getAverageRating(id);
    }
}
