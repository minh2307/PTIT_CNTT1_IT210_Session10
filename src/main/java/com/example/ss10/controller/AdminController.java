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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private EquipmentService equipmentService;
    
    @Autowired
    private LabRoomService labRoomService;
    
    @Autowired
    private BookingService bookingService;
    
    // Upload directory
    private static final String UPLOAD_DIR = "src/main/webapp/uploads/";
    
    @GetMapping("")
    public String dashboard(Model model) {
        model.addAttribute("pendingBookings", bookingService.findByStatus("PENDING"));
        model.addAttribute("totalEquipments", equipmentService.findAll().size());
        model.addAttribute("totalLabRooms", labRoomService.findAll().size());
        return "admin/dashboard";
    }
    
    // Equipment Management
    @GetMapping("/equipment")
    public String listEquipment(Model model) {
        model.addAttribute("equipments", equipmentService.findAll());
        return "admin/equipment-list";
    }
    
    @GetMapping("/equipment/add")
    public String addEquipmentForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "admin/equipment-form";
    }
    
    @GetMapping("/equipment/edit/{id}")
    public String editEquipmentForm(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thiết bị không tồn tại"));
        model.addAttribute("equipment", equipment);
        return "admin/equipment-form";
    }
    
    @PostMapping("/equipment/save")
    public String saveEquipment(@Valid @ModelAttribute("equipment") Equipment equipment,
                               BindingResult result,
                               @RequestParam(value = "imageFile", required = false) MultipartFile file,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/equipment-form";
        }
        
        // Handle file upload
        if (file != null && !file.isEmpty()) {
            try {
                // Create upload directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Generate unique filename
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = UUID.randomUUID().toString() + fileExtension;
                
                // Save file
                Path filePath = uploadPath.resolve(newFilename);
                Files.copy(file.getInputStream(), filePath);
                
                // Update equipment image URL
                equipment.setImageUrl("/uploads/" + newFilename);
                
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải lên hình ảnh: " + e.getMessage());
                return "redirect:/admin/equipment";
            }
        }
        
        equipmentService.save(equipment);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu thiết bị thành công!");
        return "redirect:/admin/equipment";
    }
    
    @GetMapping("/equipment/delete/{id}")
    public String deleteEquipment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        equipmentService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa thiết bị thành công!");
        return "redirect:/admin/equipment";
    }
    
    // Lab Room Management
    @GetMapping("/lab-rooms")
    public String listLabRooms(Model model) {
        model.addAttribute("labRooms", labRoomService.findAll());
        return "admin/lab-room-list";
    }
    
    @GetMapping("/lab-rooms/add")
    public String addLabRoomForm(Model model) {
        model.addAttribute("labRoom", new LabRoom());
        return "admin/lab-room-form";
    }
    
    @GetMapping("/lab-rooms/edit/{id}")
    public String editLabRoomForm(@PathVariable Long id, Model model) {
        LabRoom labRoom = labRoomService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Phòng lab không tồn tại"));
        model.addAttribute("labRoom", labRoom);
        return "admin/lab-room-form";
    }
    
    @PostMapping("/lab-rooms/save")
    public String saveLabRoom(@Valid @ModelAttribute("labRoom") LabRoom labRoom,
                             BindingResult result,
                             @RequestParam(value = "imageFile", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/lab-room-form";
        }
        
        // Handle file upload
        if (file != null && !file.isEmpty()) {
            try {
                // Create upload directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Generate unique filename
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = UUID.randomUUID().toString() + fileExtension;
                
                // Save file
                Path filePath = uploadPath.resolve(newFilename);
                Files.copy(file.getInputStream(), filePath);
                
                // Update lab room image URL
                labRoom.setImageUrl("/uploads/" + newFilename);
                
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải lên hình ảnh: " + e.getMessage());
                return "redirect:/admin/lab-rooms";
            }
        }
        
        labRoomService.save(labRoom);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu phòng lab thành công!");
        return "redirect:/admin/lab-rooms";
    }
    
    @GetMapping("/lab-rooms/delete/{id}")
    public String deleteLabRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        labRoomService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa phòng lab thành công!");
        return "redirect:/admin/lab-rooms";
    }
    
    // Booking Management
    @GetMapping("/bookings")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "admin/booking-list";
    }
    
    @GetMapping("/bookings/pending")
    public String listPendingBookings(Model model) {
        model.addAttribute("bookings", bookingService.findByStatus("PENDING"));
        return "admin/booking-list";
    }
    
    @GetMapping("/bookings/approve/{id}")
    public String approveBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Yêu cầu không tồn tại"));
        booking.setStatus("APPROVED");
        bookingService.save(booking);
        redirectAttributes.addFlashAttribute("successMessage", "Phê duyệt yêu cầu thành công!");
        return "redirect:/admin/bookings/pending";
    }
    
    @GetMapping("/bookings/reject/{id}")
    public String rejectBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Yêu cầu không tồn tại"));
        booking.setStatus("REJECTED");
        bookingService.save(booking);
        
        // Return equipment quantity if rejected
        if ("EQUIPMENT".equals(booking.getItemType()) && booking.getEquipmentId() != null) {
            equipmentService.updateQuantity(booking.getEquipmentId(), booking.getQuantity());
        }
        
        redirectAttributes.addFlashAttribute("successMessage", "Từ chối yêu cầu thành công!");
        return "redirect:/admin/bookings/pending";
    }
}
