package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Taluka;
import com.sankalpapp.repository.TalukaRepository;
import com.sankalpapp.service.TalukaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalukaServiceImpl implements TalukaService {

    private final TalukaRepository talukaRepository;

    @Override
    public Taluka saveTaluka(Taluka taluka) {
        return talukaRepository.save(taluka);
    }

    @Override
    public Taluka updateTaluka(Long id, Taluka taluka) {

        Taluka existing = getTalukaById(id);

        existing.setTalukaName(taluka.getTalukaName());
        existing.setDistrict(taluka.getDistrict());
        existing.setActive(taluka.getActive());

        return talukaRepository.save(existing);
    }

    @Override
    public void deleteTaluka(Long id) {
        talukaRepository.delete(getTalukaById(id));
    }

    @Override
    public Taluka getTalukaById(Long id) {

        return talukaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taluka not found."));
    }

    @Override
    public List<Taluka> getAllTalukas() {
        return talukaRepository.findAll();
    }

    @Override
    public List<Taluka> getTalukasByDistrict(Long districtId) {
        return talukaRepository.findByDistrictId(districtId);
    }
}