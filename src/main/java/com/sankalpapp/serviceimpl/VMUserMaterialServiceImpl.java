package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.response.VMMaterialDTO;
import com.sankalpapp.dto.response.VMUserMaterialResponseDTO;
import com.sankalpapp.entity.User;
import com.sankalpapp.entity.VMMaterial;
import com.sankalpapp.entity.VMUserMaterialAssociation;
import com.sankalpapp.repository.UserRepository;
import com.sankalpapp.repository.VMMaterialRepository;
import com.sankalpapp.repository.VMUserMaterialRepository;
import com.sankalpapp.service.VMUserMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VMUserMaterialServiceImpl implements VMUserMaterialService {

    @Autowired
    private UserRepository vUserRepository;

    @Autowired
    private VMMaterialRepository vmMaterialRepository;

    @Autowired
    private VMUserMaterialRepository vmUserMaterialRepository;

    @Override
    public VMUserMaterialAssociation addUserMaterialAssociation(String email, Long materialId, Double paidAmount) {
        User vUser = vUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with provided username and email"));

        VMMaterial vmMaterial = vmMaterialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        VMUserMaterialAssociation vmUserMaterialAssociation = new VMUserMaterialAssociation();
        vmUserMaterialAssociation.setVUser(vUser);
        vmUserMaterialAssociation.setVmMaterial(vmMaterial);
        if (paidAmount != null) {
            vmUserMaterialAssociation.setPaidAmount(paidAmount);
        } else {
            throw new IllegalArgumentException("Paid amount cannot be null");
        }

        return vmUserMaterialRepository.save(vmUserMaterialAssociation);
    }

    @Override
    public VMUserMaterialResponseDTO getUserAndMaterialsByUsernameAndEmail(String email) {
        // Validate the email input
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }

        Optional<User> vUser = vUserRepository.findByEmail(email);

        if (vUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        List<VMMaterialDTO> materials = vmUserMaterialRepository.findAllMaterialsByEmail(email);

        return new VMUserMaterialResponseDTO(vUser.orElse(null), materials);
    }

    @Override
    public List<VMUserMaterialAssociation> getAllUserMaterialAssociations() {
        return vmUserMaterialRepository.findAll();
    }

}
