package com.stayfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stayfinder.model.Room;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findByRoomType(String roomType);

	List<Room> findByRentLessThanEqual(int rent);
	List<Room> findByOwnerId(Long ownerId);
	List<Room> findByLocationContaining(String location);

}