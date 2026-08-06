package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.District;
import com.sankalpapp.entity.Taluka;
import com.sankalpapp.repository.DistrictRepository;
import com.sankalpapp.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public District saveDistrict(District district) {
        // 1. Check if the district has talukas
        if (district.getTalukas() != null) {
            // 2. Loop through each taluka and set the parent district
            for (Taluka taluka : district.getTalukas()) {
                taluka.setDistrict(district); // <-- This is the missing link!
            }
        }

        // 3. Now save. Hibernate will cascade the save and correctly insert the district_id
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