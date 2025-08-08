package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.BrandCreationRequest;
import com.laptopshop.laptopshop.dto.response.BrandResponse;
import com.laptopshop.laptopshop.entity.BrandEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T12:59:19+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandResponse toBrandResponse(BrandEntity brand) {
        if ( brand == null ) {
            return null;
        }

        BrandResponse.BrandResponseBuilder brandResponse = BrandResponse.builder();

        brandResponse.id( brand.getId() );
        brandResponse.name( brand.getName() );
        brandResponse.createdAt( brand.getCreatedAt() );
        brandResponse.updatedAt( brand.getUpdatedAt() );

        return brandResponse.build();
    }

    @Override
    public BrandEntity toBrand(BrandCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        BrandEntity.BrandEntityBuilder brandEntity = BrandEntity.builder();

        brandEntity.name( request.getName() );

        return brandEntity.build();
    }
}
