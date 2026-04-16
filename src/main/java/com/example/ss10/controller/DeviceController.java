package com.example.ss10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Danh sách
    @GetMapping
    public String list(Model model) {
        model.addAttribute("devices", deviceService.findAll());
        return "device/list";
    }

    // Chi tiết
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("device", deviceService.findById(id));
        return "device/detail";
    }
}
