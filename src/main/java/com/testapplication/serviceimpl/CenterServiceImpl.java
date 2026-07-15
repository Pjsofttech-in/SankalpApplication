package com.testapplication.serviceimpl;

import com.testapplication.entity.Center;
import com.testapplication.repository.CenterRepository;
import com.testapplication.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;

    @Override
    public Center saveCenter(Center center) {

        if (centerRepository.findByCenterCode(center.getCenterCode()).isPresent()) {
            throw new RuntimeException("Center code already exists.");
        }

        return centerRepository.save(center);
    }

    @Override
    public Center updateCenter(Long id, Center center) {

        Center existingCenter = getCenterById(id);

        existingCenter.setCenterName(center.getCenterName());
        existingCenter.setCenterCode(center.getCenterCode());
        existingCenter.setAddress(center.getAddress());
        existingCenter.setVillage(center.getVillage());
        existingCenter.setTaluka(center.getTaluka());
        existingCenter.setDistrict(center.getDistrict());
        existingCenter.setState(center.getState());
        existingCenter.setPincode(center.getPincode());
        existingCenter.setActive(center.getActive());
        existingCenter.setSchool(center.getSchool());

        return centerRepository.save(existingCenter);
    }

    @Override
    public void deleteCenter(Long id) {

        Center center = getCenterById(id);
        centerRepository.delete(center);
    }

    @Override
    public Center getCenterById(Long id) {

        return centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found with id : " + id));
    }

    @Override
    public List<Center> getAllCenters() {

        return centerRepository.findAll();
    }
}