package com.stayfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.stayfinder.model.Room;
import com.stayfinder.model.User;
import com.stayfinder.repository.RoomRepository;
import com.stayfinder.repository.UserRepository;
import com.stayfinder.security.JwtUtil;

import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private JwtUtil jwtUtil;

    public Room addRoom(
        String token,
        String roomType,
        int rent,
        int sharing,
        String location,
        String contact,
        String details,
        boolean parking,
        String imageUrl
    ){
    	if (token == null || !token.startsWith("Bearer ")) {
    	    throw new RuntimeException("Missing token");
    	}

    	token = token.substring(7);

    	Long ownerId = jwtUtil.extractUserId(token);

    	if(ownerId == null){
    	    throw new RuntimeException("Invalid Token - user not found");
    	}

        Room room = new Room();

        room.setRoomType(roomType);
        room.setRent(rent);
        room.setSharing(sharing);
        room.setLocation(location);
        room.setContact(contact);
        room.setDetails(details);
        room.setParking(parking);
        room.setImageUrl(imageUrl);
        room.setOwnerId(ownerId);

        return roomRepository.save(room);
    }

    public List<Map<String, Object>> getAllRooms() {

        List<Room> rooms = roomRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for(Room r : rooms){

            User owner = userRepository.findById(r.getOwnerId()).orElse(null);

            Map<String, Object> map = new HashMap<>();

            map.put("roomType", r.getRoomType());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());
            map.put("parking", r.isParking());
            map.put("location", r.getLocation());
            map.put("contact", r.getContact());
            map.put("imageUrl", r.getImageUrl());
            map.put("details", r.getDetails()); 
            map.put("id", r.getId());
            if(owner != null){
                map.put("ownerName", owner.getName());
                map.put("ownerPhone", owner.getPhone());
            }

            result.add(map);
        }

        return result;
    }
    
    public List<Map<String,Object>> getMyRooms(String token){

        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        token = token.substring(7);

        Long ownerId = jwtUtil.extractUserId(token);

        List<Room> list = roomRepository.findByOwnerId(ownerId);

        List<Map<String,Object>> result = new ArrayList<>();

        for(Room r : list){

            Map<String,Object> map = new HashMap<>();

            map.put("roomType", r.getRoomType());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());
            map.put("location", r.getLocation());
            map.put("parking", r.isParking());
            map.put("wifi", r.isWifi());
            map.put("contact", r.getContact());
            map.put("details", r.getDetails());
            map.put("imageUrl", r.getImageUrl());
            map.put("id", r.getId());
            result.add(map);
        }

        return result;
    }
    public List<Room> searchByType(String type) {
        return roomRepository.findByRoomType(type);
    }

    public List<Room> searchByRent(int rent) {
        return roomRepository.findByRentLessThanEqual(rent);
    }

    public List<Map<String, Object>> searchByLocation(String location){

        List<Room> list = roomRepository.findByLocationContaining(location);
        List<Map<String, Object>> result = new ArrayList<>();

        for(Room r : list){

            User owner = userRepository.findById(r.getOwnerId()).orElse(null);

            Map<String, Object> map = new HashMap<>();

            map.put("roomType", r.getRoomType());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());
            map.put("location", r.getLocation());
            map.put("parking", r.isParking());
            map.put("imageUrl", r.getImageUrl());

            if(owner != null){
                map.put("ownerName", owner.getName());
                map.put("ownerPhone", owner.getPhone());
            }

            result.add(map);
        }

        return result;
    }
    public void deleteRoom(Long id){
        roomRepository.deleteById(id);
    }
    
}


