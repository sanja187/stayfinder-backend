package com.stayfinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stayfinder.model.HostelReview;

@Repository
public interface HostelReviewRepository extends JpaRepository<HostelReview, Long> {

    List<HostelReview> findByHostelId(Long hostelId);

}
