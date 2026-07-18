package com.testapplication.serviceimpl;

import com.testapplication.dto.Request.CenterRequest;
import com.testapplication.dto.Response.CenterResponse;
import com.testapplication.entity.Center;
import com.testapplication.entity.District;
import com.testapplication.entity.School;
import com.testapplication.entity.Taluka;
import com.testapplication.repository.CenterRepository;
import com.testapplication.repository.DistrictRepository;
import com.testapplication.repository.SchoolRepository;
import com.testapplication.repository.TalukaRepository;
import com.testapplication.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    private final SchoolRepository schoolRepository;
    private final DistrictRepository districtRepository;
    private final TalukaRepository talukaRepository;

    @Override
    public CenterResponse saveCenter(CenterRequest request) {

        if (centerRepository.findByCenterCode(request.getCenterCode()).isPresent()) {
            throw new RuntimeException("Center code already exists.");
        }

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        Taluka taluka = talukaRepository.findById(request.getTalukaId())
                .orElseThrow(() -> new RuntimeException("Taluka not found"));

        Center center = Center.builder()
                .centerName(request.getCenterName())
                .centerCode(request.getCenterCode())
                .address(request.getAddress())
                .village(request.getVillage())
                .state(request.getState())
                .pincode(request.getPincode())
                .active(request.getActive())
                .school(school)
                .district(district)
                .taluka(taluka)
                .build();

        return mapToResponse(centerRepository.save(center));
    }

    @Override
    public CenterResponse updateCenter(Long id, CenterRequest request) {

        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found with id : " + id));

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        Taluka taluka = talukaRepository.findById(request.getTalukaId())
                .orElseThrow(() -> new RuntimeException("Taluka not found"));

        center.setCenterName(request.getCenterName());
        center.setCenterCode(request.getCenterCode());
        center.setAddress(request.getAddress());
        center.setVillage(request.getVillage());
        center.setState(request.getState());
        center.setPincode(request.getPincode());
        center.setActive(request.getActive());
        center.setSchool(school);
        center.setDistrict(district);
        center.setTaluka(taluka);

        return mapToResponse(centerRepository.save(center));
    }

    @Override
    public void deleteCenter(Long id) {

        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found with id : " + id));

        centerRepository.delete(center);
    }

    @Override
    public CenterResponse getCenterById(Long id) {

        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found with id : " + id));

        return mapToResponse(center);
    }

    @Override
    public List<CenterResponse> getAllCenters() {

        return centerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CenterResponse> getCentersByTaluka(Long talukaId) {

        return centerRepository.findByTalukaId(talukaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CenterResponse mapToResponse(Center center) {

        return CenterResponse.builder()
                .id(center.getId())
                .centerName(center.getCenterName())
                .centerCode(center.getCenterCode())
                .address(center.getAddress())
                .village(center.getVillage())
                .state(center.getState())
                .pincode(center.getPincode())
                .active(center.getActive())

                .schoolId(center.getSchool().getId())
                .schoolName(center.getSchool().getSchoolName())

                .districtId(center.getDistrict().getId())
                .districtName(center.getDistrict().getDistrictName())

                .talukaId(center.getTaluka().getId())
                .talukaName(center.getTaluka().getTalukaName())

                .build();
    }
}