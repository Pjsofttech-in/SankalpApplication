package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.TalukaRequest;
import com.sankalpapp.dto.Response.TalukaDTO;
import com.sankalpapp.dto.mapper.TalukaMapper;
import com.sankalpapp.entity.District;
import com.sankalpapp.entity.Taluka;
import com.sankalpapp.repository.DistrictRepository;
import com.sankalpapp.repository.TalukaRepository;
import com.sankalpapp.service.TalukaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalukaServiceImpl implements TalukaService {

    private final TalukaRepository talukaRepository;
    private final DistrictRepository districtRepository;

    @Override
    public TalukaDTO saveTaluka(TalukaRequest request) {
        District district = districtRepository.findById(request.getDistrictId()).orElse(null);
        Taluka entity = TalukaMapper.toEntity(request, district);
        return TalukaMapper.toDTO(talukaRepository.save(entity));
    }

    @Override
    public TalukaDTO updateTaluka(Long id, TalukaRequest request) {

        Taluka existing = talukaRepository.findById(id).orElseThrow();
        District district = districtRepository.findById(request.getDistrictId()).orElse(null);

        existing.setTalukaName(request.getTalukaName());
        existing.setDistrict(district);
        existing.setActive(request.getActive());

        return TalukaMapper.toDTO(talukaRepository.saveAndFlush(existing));
    }

    @Override
    public void deleteTaluka(Long id) {
        talukaRepository.delete(talukaRepository.findById(id).orElseThrow());
    }

    @Override
    public TalukaDTO getTalukaById(Long id) {

        return TalukaMapper.toDTO(talukaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taluka not found.")));
    }

    @Override
    public List<TalukaDTO> getAllTalukas() {
        return talukaRepository.findAll().stream().map(TalukaMapper::toDTO).toList();
    }

    @Override
    public List<TalukaDTO> getTalukasByDistrict(Long districtId) {
        return talukaRepository.findByDistrictId(districtId).stream().map(TalukaMapper::toDTO).toList();
    }
}