package com.stayfinder.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stayfinder.dto.RoommateRequest;
import com.stayfinder.model.Roommate;
import com.stayfinder.model.User;
import com.stayfinder.repository.RoommateRepository;
import com.stayfinder.repository.UserRepository;
import com.stayfinder.security.JwtUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoommateService {

	@Autowired
	private JwtUtil jwtUtil;

    @Autowired
    private RoommateRepository roommateRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Roommate addRoommate(String token, RoommateRequest request){

        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        token = token.substring(7);

        Long userId = jwtUtil.extractUserId(token);
        
        

        Roommate roommate = new Roommate();

        roommate.setDescription(request.getDescription());
        roommate.setRent(request.getRent());
        roommate.setSharing(request.getSharing());
        roommate.setGenderPreference(request.getGenderPreference());
        roommate.setLocation(request.getLocation());
        roommate.setContact(request.getContact());
        roommate.setImageUrl(request.getImageUrl());
        roommate.setPostedBy(userId);
        roommate.setDetails(request.getDetails());

        return roommateRepository.save(roommate);
    }



    public List<Map<String, Object>> getAllRoommates() {

        List<Roommate> list = roommateRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for(Roommate r : list){

            User owner = userRepository.findById(r.getPostedBy()).orElse(null);

            Map<String, Object> map = new HashMap<>();

            map.put("description", r.getDescription());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());     // ⭐ ADD
            map.put("genderPreference", r.getGenderPreference());
            map.put("location", r.getLocation());
            map.put("details", r.getDetails());     // ⭐ ADD
            map.put("contact", r.getContact());     // ⭐ ADD
            map.put("imageUrl", r.getImageUrl());   // ⭐ KEEP

            if(owner != null){
                map.put("ownerName", owner.getName());
                map.put("ownerPhone", owner.getPhone());
            } else {
                map.put("ownerName", "Unknown");   // ⭐ IMPORTANT
                map.put("ownerPhone", "N/A");
            }

            result.add(map);
        }

        return result;
    }

    public List<Map<String, Object>> searchByLocation(String location){

        List<Roommate> list = roommateRepository.findByLocationContaining(location);
        List<Map<String, Object>> result = new ArrayList<>();

        for(Roommate r : list){

            User owner = userRepository.findById(r.getPostedBy()).orElse(null);

            Map<String, Object> map = new HashMap<>();

            map.put("description", r.getDescription());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());
            map.put("genderPreference", r.getGenderPreference());
            map.put("location", r.getLocation());
            map.put("details", r.getDetails());
            map.put("contact", r.getContact());
            map.put("imageUrl", r.getImageUrl());

            if(owner != null){
                map.put("ownerName", owner.getName());
                map.put("ownerPhone", owner.getPhone());
            } else {
                map.put("ownerName", "Unknown");
                map.put("ownerPhone", "N/A");
            }

            result.add(map);
        }

        return result;
    }
    

    public List<Map<String, Object>> searchByGender(String gender){

        List<Roommate> list = roommateRepository.findByGenderPreference(gender);
        List<Map<String, Object>> result = new ArrayList<>();

        for(Roommate r : list){

            User owner = userRepository.findById(r.getPostedBy()).orElse(null); // ✅ FIXED

            Map<String, Object> map = new HashMap<>();

            map.put("description", r.getDescription());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());
            map.put("location", r.getLocation());
            map.put("genderPreference", r.getGenderPreference());
            map.put("imageUrl", r.getImageUrl());
            map.put("details", r.getDetails());
            map.put("contact", r.getContact());

            if(owner != null){
                map.put("ownerName", owner.getName());
                map.put("ownerPhone", owner.getPhone());
            } else {
                map.put("ownerName", "Unknown");
                map.put("ownerPhone", "N/A");
            }

            result.add(map);
        }

        return result;
    }
    public List<Map<String, Object>> getMyPosts(String token){

        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        token = token.substring(7);
        Long userId = jwtUtil.extractUserId(token);

        List<Roommate> list = roommateRepository.findByPostedBy(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for(Roommate r : list){

            Map<String, Object> map = new HashMap<>();

            map.put("description", r.getDescription());
            map.put("rent", r.getRent());
            map.put("sharing", r.getSharing());
            map.put("location", r.getLocation());
            map.put("imageUrl", r.getImageUrl());
            map.put("details", r.getDetails());
            map.put("contact", r.getContact());   // ⭐ ADD
            map.put("id", r.getId());
            result.add(map);
        }

        return result;
    }
    
    public void deleteRoommate(String token, Long id){

        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        token = token.substring(7);
        Long userId = jwtUtil.extractUserId(token);

        Roommate roommate = roommateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 🔐 Only owner can delete
        if(!roommate.getPostedBy().equals(userId)){
            throw new RuntimeException("You cannot delete this post");
        }

        roommateRepository.delete(roommate);
    }

}

