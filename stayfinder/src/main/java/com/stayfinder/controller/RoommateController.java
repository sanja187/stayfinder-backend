package com.stayfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.stayfinder.dto.RoommateRequest;
import com.stayfinder.model.Roommate;
import com.stayfinder.service.RoommateService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roommate")
public class RoommateController {

	@Autowired
	private RoommateService roommateService;

	@GetMapping("/all")
	public List<Map<String, Object>> getAllRoommates() {
		return roommateService.getAllRoommates();
	}

	@GetMapping("/search/gender")
	public List<Map<String,Object>> searchByGender(@RequestParam String gender){
	    return roommateService.searchByGender(gender);
	}

	@GetMapping("/search/location")
	public List<Map<String,Object>> searchByLocation(@RequestParam String location){
	    return roommateService.searchByLocation(location);
	}

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Roommate addRoommate(
	        @RequestHeader("Authorization") String token,
	        @RequestParam(value = "file", required = false) MultipartFile file,
	        @RequestParam(value = "description", required = false) String description,
	        @RequestParam(value = "rent", required = false) String rent,
	        @RequestParam(value = "sharing", required = false) String sharing,
	        @RequestParam(value = "genderPreference", required = false) String genderPreference,
	        @RequestParam(value = "location", required = false) String location,
	        @RequestParam(value = "contact", required = false) String contact,
	        @RequestParam(value = "details", required = false) String details
	) throws Exception {

	    String imagePath = null;

	    if (file != null && !file.isEmpty()) {

	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

	        String uploadDir = System.getProperty("user.dir") + "/uploads";

	        java.io.File dir = new java.io.File(uploadDir);
	        if (!dir.exists()) dir.mkdirs();

	        String fullPath = uploadDir + "/" + fileName;
	        file.transferTo(new java.io.File(fullPath));

	        // ⭐ IMPORTANT
	        imagePath = "uploads/" + fileName;
	    }

	    RoommateRequest req = new RoommateRequest();
	    req.setDescription(description);
	    req.setRent(rent);
	    req.setSharing(sharing);
	    req.setGenderPreference(genderPreference);
	    req.setLocation(location);
	    req.setContact(contact);
	    req.setImageUrl(imagePath);
	    req.setDetails(details);

	    return roommateService.addRoommate(token, req);
	}

	@GetMapping("/my")
	public List<Map<String, Object>> getMyPosts(@RequestHeader("Authorization") String token) {
		return roommateService.getMyPosts(token);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteRoommate(
	        @RequestHeader("Authorization") String token,
	        @PathVariable Long id){

	    roommateService.deleteRoommate(token, id);
	    return "Deleted";
	}

}
