package com.example.ss10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class UiController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Trang chủ");
        return "home";
    }

    @GetMapping("/devices")
    public String deviceList(Model model) {
        List<DeviceView> devices = Arrays.asList(
                new DeviceView(1, "Màn hình Dell 24 inch", "Màn hình phục vụ học tập và thực hành", 8,
                        "https://via.placeholder.com/600x400?text=Device+1"),
                new DeviceView(2, "Cáp HDMI", "Cáp kết nối máy tính với màn hình", 15,
                        "https://via.placeholder.com/600x400?text=Device+2"),
                new DeviceView(3, "Board mạch Arduino", "Thiết bị thực hành lập trình nhúng", 5,
                        "https://via.placeholder.com/600x400?text=Device+3")
        );

        model.addAttribute("pageTitle", "Danh sách thiết bị");
        model.addAttribute("devices", devices);
        return "device-list";
    }

    @GetMapping("/lab-rooms")
    public String labRoomList(Model model) {
        List<LabRoomView> rooms = Arrays.asList(
                new LabRoomView(1, "Phòng Lab A101", "Phòng máy 40 chỗ ngồi", 1,
                        "https://via.placeholder.com/600x400?text=Lab+Room+1"),
                new LabRoomView(2, "Phòng Lab B203", "Phòng thực hành mạng", 1,
                        "https://via.placeholder.com/600x400?text=Lab+Room+2"),
                new LabRoomView(3, "Phòng Lab C305", "Phòng thực hành phần mềm", 1,
                        "https://via.placeholder.com/600x400?text=Lab+Room+3")
        );

        model.addAttribute("pageTitle", "Danh sách phòng Lab");
        model.addAttribute("rooms", rooms);
        return "lab-room-list";
    }

    @GetMapping("/devices/detail")
    public String deviceDetail(Model model) {
        DeviceView device = new DeviceView(
                1,
                "Màn hình Dell 24 inch",
                "Màn hình dùng để hỗ trợ học tập, thuyết trình và thực hành các môn IT.",
                8,
                "https://via.placeholder.com/700x450?text=Device+Detail"
        );

        model.addAttribute("pageTitle", "Chi tiết thiết bị");
        model.addAttribute("device", device);
        return "device-detail";
    }

    @GetMapping("/lab-rooms/detail")
    public String labRoomDetail(Model model) {
        LabRoomView room = new LabRoomView(
                1,
                "Phòng Lab A101",
                "Phòng có 40 máy tính, máy chiếu và điều hòa, phù hợp cho học nhóm hoặc thực hành.",
                1,
                "https://via.placeholder.com/700x450?text=Lab+Room+Detail"
        );

        model.addAttribute("pageTitle", "Chi tiết phòng Lab");
        model.addAttribute("room", room);
        return "lab-room-detail";
    }

    @GetMapping("/borrow-form")
    public String borrowForm(Model model) {
        model.addAttribute("pageTitle", "Form đăng ký mượn");
        return "borrow-form";
    }

    @GetMapping("/admin/devices")
    public String adminDeviceList(Model model) {
        List<DeviceView> devices = Arrays.asList(
                new DeviceView(1, "Màn hình Dell 24 inch", "Thiết bị hiển thị", 8,
                        "https://via.placeholder.com/600x400?text=Device+1"),
                new DeviceView(2, "Cáp HDMI", "Thiết bị kết nối", 15,
                        "https://via.placeholder.com/600x400?text=Device+2"),
                new DeviceView(3, "Board mạch Arduino", "Thiết bị thực hành", 5,
                        "https://via.placeholder.com/600x400?text=Device+3")
        );

        model.addAttribute("pageTitle", "Quản lý danh sách thiết bị");
        model.addAttribute("devices", devices);
        return "admin-device-list";
    }

    @GetMapping("/admin/requests")
    public String adminRequestList(Model model) {
        List<BorrowRequestView> requests = Arrays.asList(
                new BorrowRequestView("Nguyễn Văn A", "SV001234", "Màn hình Dell 24 inch", 2, "20/04/2026", "22/04/2026", "Chờ duyệt"),
                new BorrowRequestView("Trần Thị B", "SV001235", "Phòng Lab A101", 1, "21/04/2026", "21/04/2026", "Đã duyệt"),
                new BorrowRequestView("Lê Văn C", "SV001236", "Board mạch Arduino", 1, "23/04/2026", "25/04/2026", "Từ chối")
        );

        model.addAttribute("pageTitle", "Danh sách yêu cầu mượn");
        model.addAttribute("requests", requests);
        return "admin-request-list";
    }

    public static class DeviceView {
        private Integer id;
        private String name;
        private String description;
        private Integer availableQuantity;
        private String image;

        public DeviceView(Integer id, String name, String description, Integer availableQuantity, String image) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.availableQuantity = availableQuantity;
            this.image = image;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Integer getAvailableQuantity() {
            return availableQuantity;
        }

        public String getImage() {
            return image;
        }
    }

    public static class LabRoomView {
        private Integer id;
        private String name;
        private String description;
        private Integer availableQuantity;
        private String image;

        public LabRoomView(Integer id, String name, String description, Integer availableQuantity, String image) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.availableQuantity = availableQuantity;
            this.image = image;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Integer getAvailableQuantity() {
            return availableQuantity;
        }

        public String getImage() {
            return image;
        }
    }

    public static class BorrowRequestView {
        private String studentName;
        private String studentCode;
        private String itemName;
        private Integer quantity;
        private String borrowDate;
        private String returnDate;
        private String status;

        public BorrowRequestView(String studentName, String studentCode, String itemName, Integer quantity,
                                 String borrowDate, String returnDate, String status) {
            this.studentName = studentName;
            this.studentCode = studentCode;
            this.itemName = itemName;
            this.quantity = quantity;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            this.status = status;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getStudentCode() {
            return studentCode;
        }

        public String getItemName() {
            return itemName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getBorrowDate() {
            return borrowDate;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public String getStatus() {
            return status;
        }
    }
}