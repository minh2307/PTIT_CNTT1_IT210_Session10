package com.example.ss10.controller;

import com.example.ss10.model.Booking;
import com.example.ss10.model.Equipment;
import com.example.ss10.model.LabRoom;
import com.example.ss10.service.BookingService;
import com.example.ss10.service.EquipmentService;
import com.example.ss10.service.LabRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private EquipmentService equipmentService;
    
    @Autowired
    private LabRoomService labRoomService;
    
    @Autowired
    private BookingService bookingService;
    
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("equipments", equipmentService.findAll());
        model.addAttribute("labRooms", labRoomService.findAll());
        return "student/home";
    }
    
    @GetMapping("/equipment")
    public String listEquipment(Model model) {
        model.addAttribute("equipments", equipmentService.findAll());
        return "student/equipment-list";
    }
    
    @GetMapping("/lab-rooms")
    public String listLabRooms(Model model) {
        model.addAttribute("labRooms", labRoomService.findAll());
        return "student/lab-room-list";
    }
    
    @GetMapping("/booking/equipment/{id}")
    public String bookingEquipmentForm(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thiết bị không tồn tại"));
        
        Booking booking = new Booking();
        booking.setEquipmentId(id);
        booking.setItemType("EQUIPMENT");
        booking.setQuantity(1);
        
        model.addAttribute("equipment", equipment);
        model.addAttribute("booking", booking);
        return "student/booking-form";
    }
    
    @GetMapping("/booking/lab-room/{id}")
    public String bookingLabRoomForm(@PathVariable Long id, Model model) {
        LabRoom labRoom = labRoomService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Phòng lab không tồn tại"));
        
        Booking booking = new Booking();
        booking.setLabRoomId(id);
        booking.setItemType("LAB_ROOM");
        booking.setQuantity(1);
        
        model.addAttribute("labRoom", labRoom);
        model.addAttribute("booking", booking);
        return "student/booking-form";
    }
    
    @PostMapping("/booking")
    public String submitBooking(@Valid @ModelAttribute("booking") Booking booking, 
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        // Custom validation for return date
        if (booking.getBorrowDate() != null && booking.getReturnDate() != null) {
            if (!booking.getReturnDate().isAfter(booking.getBorrowDate())) {
                result.rejectValue("returnDate", "error.booking", "Ngày dự kiến trả phải sau ngày dự kiến nhận");
            }
        }
        
        // Check quantity for equipment
        if ("EQUIPMENT".equals(booking.getItemType()) && booking.getEquipmentId() != null) {
            Equipment equipment = equipmentService.findById(booking.getEquipmentId()).orElse(null);
            if (equipment != null && booking.getQuantity() != null) {
                if (booking.getQuantity() > equipment.getAvailableQuantity()) {
                    result.rejectValue("quantity", "error.booking", 
                        "Số lượng muốn mượn không được vượt quá số lượng tồn kho (" + equipment.getAvailableQuantity() + ")");
                }
            }
        }
        
        if (result.hasErrors()) {
            // Add equipment or lab room info back to model
            if ("EQUIPMENT".equals(booking.getItemType()) && booking.getEquipmentId() != null) {
                model.addAttribute("equipment", equipmentService.findById(booking.getEquipmentId()).orElse(null));
            } else if ("LAB_ROOM".equals(booking.getItemType()) && booking.getLabRoomId() != null) {
                model.addAttribute("labRoom", labRoomService.findById(booking.getLabRoomId()).orElse(null));
            }
            return "student/booking-form";
        }
        
        // Save booking
        bookingService.save(booking);
        
        // Update equipment quantity if needed
        if ("EQUIPMENT".equals(booking.getItemType()) && booking.getEquipmentId() != null) {
            equipmentService.updateQuantity(booking.getEquipmentId(), -booking.getQuantity());
        }
        
        // Add flash message for bonus feature
        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Yêu cầu của bạn đã được gửi và đang chờ xử lý.");
        
        return "redirect:/student";
    }
}
