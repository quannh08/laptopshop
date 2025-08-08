package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.CategoryResponse;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.entity.CategoryEntity;
import com.laptopshop.laptopshop.entity.ProductEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T12:59:22+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public ProductResponse toProductResponse(ProductEntity product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.brandResponse( brandMapper.toBrandResponse( product.getBrand() ) );
        productResponse.categoryResponses( categoryEntitySetToCategoryResponseSet( product.getCategories() ) );
        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.price( product.getPrice() );
        productResponse.importPrice( product.getImportPrice() );
        productResponse.image( product.getImage() );
        productResponse.stock( product.getStock() );
        productResponse.createdAt( product.getCreatedAt() );
        productResponse.updatedAt( product.getUpdatedAt() );
        productResponse.description( product.getDescription() );

        return productResponse.build();
    }

    protected Set<CategoryResponse> categoryEntitySetToCategoryResponseSet(Set<CategoryEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<CategoryResponse> set1 = new LinkedHashSet<CategoryResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CategoryEntity categoryEntity : set ) {
            set1.add( categoryMapper.toCategoryResponse( categoryEntity ) );
        }

        return set1;
    }
}
