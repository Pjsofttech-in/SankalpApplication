package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.Marquee;
import com.sankalpapp.dynamicProfile.service.MarqueeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marquee")
@RequiredArgsConstructor
public class MarqueeController {

    private final MarqueeService marqueeService;

    @GetMapping
    public ResponseEntity<Marquee> getMarquee() {

        Marquee marquee = marqueeService.getMarquee();

        if (marquee == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(marquee);
    }

    @PutMapping
    public ResponseEntity<Marquee> updateMarquee(
            @RequestBody Marquee marquee) {

        return ResponseEntity.ok(
                marqueeService.updateMarquee(marquee)
        );
    }
}