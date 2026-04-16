package com.example.ss10.service;

import com.example.ss10.model.LabRoom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LabRoomService {
    private static final List<LabRoom> labRooms = new ArrayList<>();
    private static Long nextId = 1L;
    
    static {
        // Initialize with sample data
        labRooms.add(new LabRoom(nextId++, "Phòng Lab 101", "Phòng thực hành máy tính 1", "/images/lab101.jpg", 30));
        labRooms.add(new LabRoom(nextId++, "Phòng Lab 102", "Phòng thực hành máy tính 2", "/images/lab102.jpg", 30));
        labRooms.add(new LabRoom(nextId++, "Phòng Lab 201", "Phòng thực hành mạng", "/images/lab201.jpg", 25));
        labRooms.add(new LabRoom(nextId++, "Phòng Lab 202", "Phòng thực hành phần cứng", "/images/lab202.jpg", 20));
    }
    
    public List<LabRoom> findAll() {
        return new ArrayList<>(labRooms);
    }
    
    public Optional<LabRoom> findById(Long id) {
        return labRooms.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }
    
    public void save(LabRoom labRoom) {
        if (labRoom.getId() == null) {
            labRoom.setId(nextId++);
            labRooms.add(labRoom);
        } else {
            Optional<LabRoom> existing = findById(labRoom.getId());
            if (existing.isPresent()) {
                int index = labRooms.indexOf(existing.get());
                labRooms.set(index, labRoom);
            }
        }
    }
    
    public void deleteById(Long id) {
        labRooms.removeIf(l -> l.getId().equals(id));
    }
}
