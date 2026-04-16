package com.example.ss10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabRoomService {

    @Autowired
    private LabRoomRepository labRoomRepository;

    public List<LabRoom> findAll() {
        return labRoomRepository.findAll();
    }

    public LabRoom findById(Long id) {
        return labRoomRepository.findById(id).orElse(null);
    }
}
