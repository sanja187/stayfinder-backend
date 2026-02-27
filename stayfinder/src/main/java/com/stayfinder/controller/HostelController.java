package com.stayfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.stayfinder.model.Hostel;
import com.stayfinder.service.HostelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hostel")
public class HostelController {

    @Autowired
    private HostelService hostelService;

    @PostMapping(value="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Hostel addHostel(

        @RequestHeader("Authorization") String token,

        @RequestParam String hostelName,
        @RequestParam String gender,
        @RequestParam int rent,
        @RequestParam String sharingType,
        @RequestParam String contact,
        @RequestParam String location,
        @RequestParam Boolean hotWater,
        @RequestParam String details,
        @RequestParam boolean wifi,
        @RequestParam boolean mess,
        @RequestParam boolean laundry,
        @RequestParam boolean ac,
        @RequestParam(required = false) MultipartFile file

    ) throws Exception {

        String imagePath = null;

        if (file != null && !file.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            String uploadDir = System.getProperty("user.dir") + "/uploads";

            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fullPath = uploadDir + "/" + fileName;
            file.transferTo(new java.io.File(fullPath));

            imagePath = "uploads/" + fileName;
        }

        return hostelService.addHostel(
                token,
                hostelName,
                gender,
                rent,
                sharingType,
                contact,
                location,
                hotWater,
                details,
                wifi,
                mess,
                laundry,
                ac,
                imagePath
        );
    }
    
    @GetMapping("/my")
    public List<Map<String,Object>> getMyHostels(@RequestHeader("Authorization") String token){
        return hostelService.getMyHostels(token);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllHostels() {
        return hostelService.getAllHostels();
    }

    @GetMapping("/search/gender")
    public List<Hostel> searchByGender(@RequestParam String gender) {
        return hostelService.searchByGender(gender);
    }

    @GetMapping("/search/rent")
    public List<Hostel> searchByRent(@RequestParam int rent) {
        return hostelService.searchByRent(rent);
    }

    @GetMapping("/search/location")
    public List<Hostel> searchByLocation(@RequestParam String location) {
        return hostelService.searchByLocation(location);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteHostel(@PathVariable Long id){
        hostelService.deleteHostel(id);
    }
}