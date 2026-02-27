package com.stayfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stayfinder.model.Roommate;
import java.util.List;

public interface RoommateRepository extends JpaRepository<Roommate, Long> {

	List<Roommate> findByGenderPreference(String genderPreference);
	List<Roommate> findByLocationContaining(String location);
	List<Roommate> findByPostedBy(Long userId);

}
