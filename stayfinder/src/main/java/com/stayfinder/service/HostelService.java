package com.stayfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stayfinder.model.Hostel;
import com.stayfinder.model.User;
import com.stayfinder.repository.HostelRepository;
import com.stayfinder.repository.UserRepository;
import com.stayfinder.security.JwtUtil;

import java.util.*;

@Service
public class HostelService {

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public List<Map<String,Object>> getAllHostels(){

        List<Hostel> list = hostelRepository.findAll();
        List<Map<String,Object>> result = new ArrayList<>();

        for(Hostel h : list){

            Map<String,Object> map = new HashMap<>();
            map.put("id", h.getId()); 
            map.put("hostelName", h.getHostelName());
            map.put("gender", h.getGender());
            map.put("rent", h.getRent());
            map.put("sharingType", h.getSharingType());
            map.put("location", h.getLocation());
            map.put("hotWater", h.getHotWater());
            map.put("details", h.getDetails());
            map.put("wifi", h.isWifi());
            map.put("mess", h.isMess());
            map.put("laundry", h.isLaundry());
            map.put("ac", h.isAc());
            map.put("imageUrl", h.getImageUrl());

            User owner = null;

            if(h.getOwnerId() != null){
                owner = userRepository.findById(h.getOwnerId()).orElse(null);
            }

            if(owner != null){
                map.put("ownerName", owner.getName());
                map.put("ownerPhone", owner.getPhone());
            }else{
                map.put("ownerName", "Unknown");
                map.put("ownerPhone", "N/A");
            }

            result.add(map);
        }

        return result;
    }


    public Hostel addHostel(
            String token,
            String hostelName,
            String gender,
            int rent,
            String sharingType,
            String contact,
            String location,
            Boolean hotWater,
            String details,
            boolean wifi,
            boolean mess,
            boolean laundry,
            boolean ac,
            String imageUrl
    ){

        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        token = token.substring(7);

        Long ownerId = jwtUtil.extractUserId(token);

        if(ownerId == null){
            throw new RuntimeException("Invalid Token — OwnerId null");
        }

        Hostel hostel = new Hostel();

        hostel.setHostelName(hostelName);
        hostel.setGender(gender);
        hostel.setRent(rent);
        hostel.setSharingType(sharingType);
        hostel.setContact(contact);
        hostel.setLocation(location);
        hostel.setHotWater(hotWater);
        hostel.setDetails(details);
        hostel.setWifi(wifi);
        hostel.setMess(mess);
        hostel.setLaundry(laundry);
        hostel.setAc(ac);
        hostel.setImageUrl(imageUrl);

        hostel.setOwnerId(ownerId);   // ⭐ THIS MUST SAVE

        return hostelRepository.save(hostel);
    }
    
    public List<Map<String,Object>> getMyHostels(String token){

        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        token = token.substring(7);

        Long ownerId = jwtUtil.extractUserId(token);

        List<Hostel> list = hostelRepository.findByOwnerId(ownerId);

        List<Map<String,Object>> result = new ArrayList<>();

        for(Hostel h : list){

            Map<String,Object> map = new HashMap<>();

            map.put("hostelName", h.getHostelName());
            map.put("gender", h.getGender());
            map.put("rent", h.getRent());
            map.put("sharingType", h.getSharingType());
            map.put("location", h.getLocation());
            map.put("wifi", h.isWifi());
            map.put("mess", h.isMess());
            map.put("laundry", h.isLaundry());
            map.put("ac", h.isAc());
            map.put("hotWater", h.getHotWater());
            map.put("details", h.getDetails());
            map.put("imageUrl", h.getImageUrl());
            map.put("id", h.getId()); 
            result.add(map);
        }

        return result;
    }

    public List<Hostel> searchByGender(String gender) {
        return hostelRepository.findByGender(gender);
    }

    public List<Hostel> searchByRent(int rent) {
        return hostelRepository.findByRentLessThanEqual(rent);
    }

    public List<Hostel> searchByLocation(String location) {
        return hostelRepository.findByLocationContaining(location);
    }
    
    public void deleteHostel(Long id){
        hostelRepository.deleteById(id);
    }
}