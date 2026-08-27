package com.sankalpapp.controller;

import com.sankalpapp.entity.Section;
import com.sankalpapp.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    // SAVE SECTION
    @PostMapping
    public Section saveSection
    (@RequestBody Section section) {
        return sectionService.saveSection(section);
    }

    // GET ALL SECTIONS
    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    // GET SECTION BY ID
    @GetMapping("/{id}")
    public Section getSectionById
    (@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    // DELETE SECTION
    @DeleteMapping("/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return "Section Deleted Successfully";
    }
}