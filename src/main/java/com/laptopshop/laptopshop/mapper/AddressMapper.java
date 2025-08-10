package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.AddressRequest;
import com.laptopshop.laptopshop.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressEntity toAddress(AddressRequest addressRequest);
}
