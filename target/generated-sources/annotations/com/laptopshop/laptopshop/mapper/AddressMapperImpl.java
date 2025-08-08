package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.AddressRequest;
import com.laptopshop.laptopshop.entity.AddressEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T12:59:19+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressEntity toAddress(AddressRequest addressRequest) {
        if ( addressRequest == null ) {
            return null;
        }

        AddressEntity.AddressEntityBuilder addressEntity = AddressEntity.builder();

        addressEntity.addressLine( addressRequest.getAddressLine() );
        addressEntity.ward( addressRequest.getWard() );
        addressEntity.city( addressRequest.getCity() );
        addressEntity.fullName( addressRequest.getFullName() );

        return addressEntity.build();
    }
}
