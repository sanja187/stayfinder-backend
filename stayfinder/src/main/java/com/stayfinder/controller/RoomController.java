package com.stayfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.stayfinder.model.Room;
import com.stayfinder.service.RoomService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping(value="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Room addRoom(

        @RequestHeader("Authorization") String token,

        @RequestParam String roomType,
        @RequestParam int rent,
        @RequestParam int sharing,
        @RequestParam String location,
        @RequestParam String contact,
        @RequestParam String details,
        @RequestParam boolean parking,
        @RequestParam(required = false) MultipartFile file

    ) throws Exception {

        String imagePath = null;

        if(file != null && !file.isEmpty()){

            String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();

            String uploadDir = System.getProperty("user.dir")+"/uploads";

            java.io.File dir = new java.io.File(uploadDir);
            if(!dir.exists()) dir.mkdirs();

            String fullPath = uploadDir+"/"+fileName;
            file.transferTo(new java.io.File(fullPath));

            imagePath = "uploads/"+fileName;
        }

        return roomService.addRoom(
            token,
            roomType,
            rent,
            sharing,
            location,
            contact,
            details,
            parking,
            imagePath
        );
    }
    
    @GetMapping("/my")
    public List<Map<String,Object>> getMyRooms(@RequestHeader("Authorization") String token){
        return roomService.getMyRooms(token);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllRooms() {
        return roomService.getAllRooms();
    }
    @GetMapping("/search/type")
    public List<Room> searchByType(@RequestParam String type) {
    	return roomService.searchByType(type);
    }

    @GetMapping("/search/rent")
    public List<Room> searchByRent(@RequestParam int rent) {
    	return roomService.searchByRent(rent);
    }

    @GetMapping("/search/location")
    public List<Map<String,Object>> searchByLocation(@RequestParam String location){
        return roomService.searchByLocation(location);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
    }
}

