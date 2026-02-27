package com.stayfinder.dto;

import lombok.Data;

@Data
public class HostelRequest {

    private String hostelName;
    private String gender;
    private int rent;
    private int sharingType;

    private boolean mess;
    private double messRating;

    private boolean wifi;
    private boolean ac;
    private boolean laundry;

    private String location;
    private String contact;
    private String imageUrl;

    private Long ownerId;
    private Boolean hotWater;
    private String details;
    

	public Boolean getHotWater() {
		return hotWater;
	}

	public void setHotWater(Boolean hotWater) {
		this.hotWater = hotWater;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getSharingType() {
		return sharingType;
	}

	public void setSharingType(int sharingType) {
		this.sharingType = sharingType;
	}

	public boolean isMess() {
		return mess;
	}

	public void setMess(boolean mess) {
		this.mess = mess;
	}

	public double getMessRating() {
		return messRating;
	}

	public void setMessRating(double messRating) {
		this.messRating = messRating;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isAc() {
		return ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public boolean isLaundry() {
		return laundry;
	}

	public void setLaundry(boolean laundry) {
		this.laundry = laundry;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
    
}
