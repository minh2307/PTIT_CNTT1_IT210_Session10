package com.example.ss10.model;

import com.example.ss10.model.enums.LabRoomStatus;

public class LabRoom {

    private Long id;
    private String roomCode;
    private String roomName;
    private String location;
    private String description;
    private String imageUrl;
    private Integer capacity;
    private LabRoomStatus status = LabRoomStatus.AVAILABLE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LabRoomStatus getStatus() {
        return status;
    }

    public void setStatus(LabRoomStatus status) {
        this.status = status;
    }
}
