//package com.sankalpapp.dto.mapper;
//
//import com.sankalpapp.dto.response.AddressDTO;
//import com.sankalpapp.entity.User;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AddressMapper {
//
//    // Convert Address entity to AddressDTO
//    public AddressDTO toDTO(User user) {
//        AddressDTO addressDTO = new AddressDTO();
//        addressDTO.setId((long) user.getId());
//        addressDTO.setState(user);
//        addressDTO.setDistrict(address.getDistrict());
//        addressDTO.setArea(address.getArea());
//        addressDTO.setCity(address.getCity());
//        addressDTO.setPincode(address.getPincode());
//        addressDTO.setLandmark(address.getLandmark());
//        return addressDTO;
//    }
//
//    // Convert AddressDTO to Address entity
//    public Address toEntity(AddressDTO addressDTO) {
//        Address address = new Address();
//        address.setId(addressDTO.getId());  // Directly use Long without conversion
//        address.setAddress(addressDTO.getAddress());
//        address.setState(addressDTO.getState());
//        address.setDistrict(addressDTO.getDistrict());
//        address.setArea(addressDTO.getArea());
//        address.setCity(addressDTO.getCity());
//        address.setPincode(addressDTO.getPincode());
//        address.setLandmark(addressDTO.getLandmark());
//        return address;
//    }
//
//}
