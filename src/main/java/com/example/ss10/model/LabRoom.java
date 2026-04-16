package com.example.ss10.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LabRoom {
    private Long id;
    
    @NotBlank(message = "Tên phòng lab không được để trống")
    private String name;
    
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
    
    private String imageUrl;
    
    @NotNull(message = "Sức chứa không được để trống")
    private Integer capacity;
    
    public LabRoom() {}
    
    public LabRoom(Long id, String name, String description, String imageUrl, Integer capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.capacity = capacity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
}
