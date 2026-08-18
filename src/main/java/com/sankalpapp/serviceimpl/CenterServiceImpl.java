package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.CenterRequest;
import com.sankalpapp.dto.Response.CenterDTO;
import com.sankalpapp.entity.Center;
import com.sankalpapp.entity.District;
import com.sankalpapp.entity.Taluka;
import com.sankalpapp.repository.CenterRepository;
import com.sankalpapp.repository.DistrictRepository;
import com.sankalpapp.repository.StudentRepository;
import com.sankalpapp.repository.TalukaRepository;
import com.sankalpapp.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    private final DistrictRepository districtRepository;
    private final TalukaRepository talukaRepository;
    private final StudentRepository studentRepository;

    @Override
    public CenterDTO saveCenter(CenterRequest request) {

        if (centerRepository.findByCenterCode(request.getCenterCode()).isPresent()) {
            throw new RuntimeException("Center code already exists.");
        }

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
                .district(district)
                .taluka(taluka)
                .build();

        return mapToResponse(centerRepository.save(center));
    }

    @Override
    public CenterDTO updateCenter(Long id, CenterRequest request) {

        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found with id : " + id));

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
    public CenterDTO getCenterById(Long id) {

        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found with id : " + id));

        return mapToResponse(center);
    }

    @Override
    public List<CenterDTO> getAllCenters() {

        return centerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CenterDTO> getCentersByTaluka(Long talukaId) {

        return centerRepository.findByTalukaId(talukaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSchoolsByCenter(Long centerId) {
        return studentRepository.findUniqueSchoolsByCenterTaluka(centerId);
    }

    private CenterDTO mapToResponse(Center center) {

        return CenterDTO.builder()
                .id(center.getId())
                .centerName(center.getCenterName())
                .centerCode(center.getCenterCode())
                .address(center.getAddress())
                .village(center.getVillage())
                .state(center.getState())
                .pincode(center.getPincode())
                .active(center.getActive())

                .districtId(center.getDistrict().getId())
                .districtName(center.getDistrict().getDistrictName())

                .talukaId(center.getTaluka().getId())
                .talukaName(center.getTaluka().getTalukaName())

                .build();
    }
}