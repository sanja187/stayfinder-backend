package com.stayfinder.dto;


import lombok.Data;

@Data
public class RoommateRequest {

    private String description;
    private String rent;
    private String genderPreference;
    private Long ownerId;
    private String location;
    private String contact;
    private String sharing;
    private String imageUrl;
    private String details;

    public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSharing() {
		return sharing;
	}

	public void setSharing(String sharing2) {
		this.sharing = sharing2;
	}

	private Long postedBy;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent2) {
		this.rent = rent2;
	}

	public String getGenderPreference() {
		return genderPreference;
	}

	public void setGenderPreference(String genderPreference) {
		this.genderPreference = genderPreference;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Long getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(Long postedBy) {
		this.postedBy = postedBy;
	}

	public Long getOwnerId() {
	    return ownerId;
	}

	public void setOwnerId(Long ownerId) {
	    this.ownerId = ownerId;
	}
    
    
}
