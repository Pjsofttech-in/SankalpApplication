package com.testapplication.serviceimpl;

import com.testapplication.entity.District;
import com.testapplication.repository.DistrictRepository;
import com.testapplication.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public District saveDistrict(District district) {
        return districtRepository.save(district);
    }

    @Override
    public District updateDistrict(Long id, District district) {

        District existing = getDistrictById(id);

        existing.setDistrictName(district.getDistrictName());
        existing.setActive(district.getActive());

        return districtRepository.save(existing);
    }

    @Override
    public void deleteDistrict(Long id) {
        districtRepository.delete(getDistrictById(id));
    }

    @Override
    public District getDistrictById(Long id) {

        return districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found."));
    }

    @Override
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }
}