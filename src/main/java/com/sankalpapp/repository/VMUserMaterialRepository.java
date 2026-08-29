package com.sankalpapp.repository;

import com.sankalpapp.dto.response.VMMaterialDTO;
import com.sankalpapp.entity.VMUserMaterialAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VMUserMaterialRepository extends JpaRepository<VMUserMaterialAssociation, Long> {

    @Query("SELECT v.paidAmount FROM VMUserMaterialAssociation v WHERE v.vUser.email = :email")
    Double findPaidAmountByUsernameAndEmail(@Param("email") String email);

    @Query("SELECT new com.sankalpapp.dto.response.VMMaterialDTO(vm.id, vm.materialtype, vm.pdfFile, vm.thumbnailFile, " +
            "vm.saveToDevice, vm.demoPdf, vm.status, vm.mrp, vm.price, vm.chapterName, vm.validity, vm.createdDate, " +
            "vm.categoryName, vm.subcategoryName, v.paidAmount) " +
            "FROM VMUserMaterialAssociation v " +
            "JOIN v.vmMaterial vm " +
            "WHERE v.vUser.email = :email")
    List<VMMaterialDTO> findAllMaterialsByEmail(@Param("email") String email);

}
