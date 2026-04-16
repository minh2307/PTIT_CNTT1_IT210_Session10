package com.example.ss10.service;

import com.example.ss10.model.Equipment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private static final List<Equipment> equipments = new ArrayList<>();
    private static Long nextId = 1L;
    
    static {
        // Initialize with sample data
        equipments.add(new Equipment(nextId++, "Màn hình rời 24 inch", "Màn hình LCD 24 inch, độ phân giải Full HD", "/images/monitor.jpg", 10));
        equipments.add(new Equipment(nextId++, "Cáp HDMI", "Cáp HDMI dài 1.5m", "/images/hdmi-cable.jpg", 25));
        equipments.add(new Equipment(nextId++, "Cáp USB-C", "Cáp USB-C dài 1m", "/images/usb-c-cable.jpg", 30));
        equipments.add(new Equipment(nextId++, "Board mạch Arduino", "Board mạch Arduino Uno R3", "/images/arduino.jpg", 15));
        equipments.add(new Equipment(nextId++, "Loa di động", "Loa Bluetooth di động", "/images/speaker.jpg", 8));
    }
    
    public List<Equipment> findAll() {
        return new ArrayList<>(equipments);
    }
    
    public Optional<Equipment> findById(Long id) {
        return equipments.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }
    
    public void save(Equipment equipment) {
        if (equipment.getId() == null) {
            equipment.setId(nextId++);
            equipments.add(equipment);
        } else {
            Optional<Equipment> existing = findById(equipment.getId());
            if (existing.isPresent()) {
                int index = equipments.indexOf(existing.get());
                equipments.set(index, equipment);
            }
        }
    }
    
    public void deleteById(Long id) {
        equipments.removeIf(e -> e.getId().equals(id));
    }
    
    public void updateQuantity(Long id, Integer quantityChange) {
        Optional<Equipment> equipment = findById(id);
        if (equipment.isPresent()) {
            Equipment eq = equipment.get();
            eq.setAvailableQuantity(eq.getAvailableQuantity() + quantityChange);
        }
    }
}
