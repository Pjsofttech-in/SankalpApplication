package com.sankalpapp.controller;

import com.sankalpapp.entity.Subject;
import com.sankalpapp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("https://mahastudy.in")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://mahastudy.in",
        "http://localhost:5175",

})
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    //  Create
    @PostMapping("/createSubject")
    public ResponseEntity<Subject> create(@RequestBody Subject subject) {
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    //  Update
    @PutMapping("/updatesubjectbyid/{id}")
    public ResponseEntity<Subject> update(
            @PathVariable Long id,
            @RequestBody Subject subject) {
        return ResponseEntity.ok(subjectService.updateSubject(id, subject));
    }

    //  Get All
    @GetMapping("/GetAllSubjects")
    public ResponseEntity<List<Subject>> getAll() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    //  Get By Id
    @GetMapping("/getSubjectById/{id}")
    public ResponseEntity<Subject> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    //  Delete
    @DeleteMapping("/DeleteSubject/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject deleted successfully");
    }
}

