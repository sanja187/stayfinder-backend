package com.stayfinder.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfinder.model.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

	List<Hostel> findByGender(String gender);
	
	List<Hostel> findByOwnerId(Long ownerId);
	List<Hostel> findByRentLessThanEqual(int rent);

	List<Hostel> findByLocationContaining(String location);

}
