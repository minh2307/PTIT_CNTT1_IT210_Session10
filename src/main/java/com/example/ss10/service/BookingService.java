package com.example.ss10.service;

import com.example.ss10.model.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private static final List<Booking> bookings = new ArrayList<>();
    private static Long nextId = 1L;
    
    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }
    
    public List<Booking> findByStatus(String status) {
        return bookings.stream()
                .filter(b -> status.equals(b.getStatus()))
                .collect(Collectors.toList());
    }
    
    public Optional<Booking> findById(Long id) {
        return bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }
    
    public void save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(nextId++);
            bookings.add(booking);
        } else {
            Optional<Booking> existing = findById(booking.getId());
            if (existing.isPresent()) {
                int index = bookings.indexOf(existing.get());
                bookings.set(index, booking);
            }
        }
    }
    
    public void deleteById(Long id) {
        bookings.removeIf(b -> b.getId().equals(id));
    }
    
    public boolean hasDateCollision(Long labRoomId, LocalDate borrowDate, LocalDate returnDate, Long excludeId) {
        return bookings.stream()
                .filter(b -> "LAB_ROOM".equals(b.getItemType()))
                .filter(b -> labRoomId.equals(b.getLabRoomId()))
                .filter(b -> !b.getId().equals(excludeId))
                .filter(b -> !"REJECTED".equals(b.getStatus()))
                .anyMatch(b -> isDateOverlap(b.getBorrowDate(), b.getReturnDate(), borrowDate, returnDate));
    }
    
    private boolean isDateOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
    
    public List<Booking> findByItemType(String itemType) {
        return bookings.stream()
                .filter(b -> itemType.equals(b.getItemType()))
                .collect(Collectors.toList());
    }
}
