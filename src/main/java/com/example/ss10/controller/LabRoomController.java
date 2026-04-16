package com.example.ss10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lab-rooms")
public class LabRoomController {

    @Autowired
    private LabRoomService labRoomService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("rooms", labRoomService.findAll());
        return "labroom/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("room", labRoomService.findById(id));
        return "labroom/detail";
    }
}
