package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.Marquee;
import com.sankalpapp.dynamicProfile.repository.MarqueeRepository;
import com.sankalpapp.dynamicProfile.service.MarqueeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarqueeServiceImpl implements MarqueeService {

    private static final Long MARQUEE_ID = 1L;

    private final MarqueeRepository marqueeRepository;

    @Override
    public Marquee getMarquee() {

        return marqueeRepository.findById(MARQUEE_ID)
                .orElse(null);
    }

    @Override
    @Transactional
    public Marquee updateMarquee(Marquee marquee) {

        Marquee existingMarquee = marqueeRepository
                .findById(MARQUEE_ID)
                .orElse(new Marquee());

        existingMarquee.setId(MARQUEE_ID);
        existingMarquee.setName(marquee.getName());

        return marqueeRepository.save(existingMarquee);
    }
}