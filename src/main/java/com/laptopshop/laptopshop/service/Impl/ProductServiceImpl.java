package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.dto.request.ProductCreationRequest;
import com.laptopshop.laptopshop.dto.request.ProductUpdateRequest;
import com.laptopshop.laptopshop.dto.response.ProductPageResponse;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.entity.BrandEntity;
import com.laptopshop.laptopshop.entity.CategoryEntity;
import com.laptopshop.laptopshop.entity.ProductDescription;
import com.laptopshop.laptopshop.entity.ProductEntity;
import com.laptopshop.laptopshop.exception.InvalidDataException;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.mapper.BrandMapper;
import com.laptopshop.laptopshop.mapper.CategoryMapper;
import com.laptopshop.laptopshop.repository.BrandRepository;
import com.laptopshop.laptopshop.repository.CategoryRepository;
import com.laptopshop.laptopshop.repository.ProductRepository;
import com.laptopshop.laptopshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    private final BrandMapper brandMapper;

    private final CategoryMapper categoryMapper;

    private final FileStorageService fileStorageService;

    @Value("${app.image-api-base-url:http://localhost:8080/api/images/}")
    private String imageApiBaseUrl;
    @Override
    public ProductPageResponse findAll(String keyword, String sort, int page, int size, BigDecimal minPrice, BigDecimal maxPrice, Long brandId, Long categoryId) {
        log.info("find all product");

        //Sort
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"id");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,columnName);
                }
                else{
                    order = new Sort.Order(Sort.Direction.DESC,columnName);
                    log.info("desc");
                }
            }
        }

        int pageNo = 0;
        if(page > 0){
            pageNo = page - 1;
        }

        //Paging
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(order));

        Page<ProductEntity> entityPage;

        if(StringUtils.hasLength(keyword)||sort!=null||minPrice!=null||maxPrice!=null||categoryId!=null||brandId!=null){
            log.info("not find all");
            String searchKeyword = null;
            if(StringUtils.hasLength(keyword)){
                searchKeyword = "%"+keyword.toLowerCase()+"%";
            }
            entityPage = productRepository.searchByKeywordAndPriceRange(keyword,minPrice,maxPrice,brandId,categoryId,pageable);
        }
        else{
            log.info("find all");
            entityPage = productRepository.findAll(pageable);
        }

        return getProductPageResponse(page,size,entityPage);
    }

    @Override
    public ProductResponse findById(Long id) {
        ProductEntity prd = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "+id));

        return toProductRes(prd);
    }

    @Override
    public ProductResponse findByName(String name) {
        return null;
    }

    @Override
    public ProductPageResponse findByPriceBetween(Double minPrice, Double maxPrice) {
        return null;
    }

    @Override
    public List<ProductResponse> findByBrandId(Long brandId) {
        log.info("find by brand id: {}", brandId);
        List<ProductEntity> prds = productRepository.findByBrandId(brandId);

        return prds.stream().map(this::toProductRes).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByCategoryId(Set<Long> categoryIds) {
        log.info("find by category id: {}", categoryIds);
        List<ProductEntity> prds = productRepository.findByCategories_Id(categoryIds);
        return prds.stream().map(this::toProductRes).collect(Collectors.toList());
    }

    @Override
    public long save(ProductCreationRequest req, MultipartFile imageFile) throws IOException {
        log.info("Saving product {}", req);

        // Check if category exists
        Set<CategoryEntity> categoryEntities = req.getCategoryId().stream()
                .map(categoryID ->categoryRepository.findById(categoryID)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found")))
                .collect(Collectors.toSet());

//        CategoryEntity category = categoryRepository.findById(req.getCategoryId())
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // Check if brand exists
        BrandEntity brand = brandRepository.findById(req.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        // Kiểm tra tên sản phẩm đã tồn tại
        ProductEntity existingProduct = productRepository.findByName(req.getName());
        if (existingProduct != null) {
            throw new InvalidDataException("Name of product already exists");
        }

        //Desciption
        ProductDescription prdDes = ProductDescription.builder()
                .CPUtype(req.getDescription().getCPUtype())
                .hardDrive(req.getDescription().getHardDrive())
                .ramCapacity(req.getDescription().getRamCapacity())
                .screenSize(req.getDescription().getScreenSize())
                .operatingSystem(req.getDescription().getOperatingSystem())
                .build();


        ProductEntity product = ProductEntity.builder()
                .name(req.getName())
                .price(req.getPrice())
                .importPrice(req.getImportPrice())
                .stock(req.getStock())
                .description(prdDes)
                .categories(categoryEntities)
                .brand(brand) //
                .build();

        // Xử lý file ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(imageFile);
            product.setImage(imageApiBaseUrl + fileName);
        } else {
            product.setImage(req.getImage());
        }

        productRepository.save(product);
        log.info("Saved product {}", product);

        return product.getId();
    }

    @Override
    public void update(ProductUpdateRequest req, MultipartFile imageFile) throws IOException {
        log.info("Updating product: {}", req);
        ProductEntity existingProduct = productRepository.findById(req.getId())
                .orElseThrow(()-> new ResourceNotFoundException("product not existed"));

        Set<CategoryEntity> categoryEntities = req.getCategoryId().stream()
                .map(categoryID ->categoryRepository.findById(categoryID)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found")))
                .collect(Collectors.toSet());

//        CategoryEntity category = categoryRepository.findById(req.getCategoryId())
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // Check if brand exists
        BrandEntity brand = brandRepository.findById(req.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        //Set image
        String imageUrl = existingProduct.getImage(); // mặc định giữ ảnh cũ
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(imageFile);
            imageUrl = imageApiBaseUrl + fileName;
        } else if (req.getImage() != null && !req.getImage().isBlank()) {
            imageUrl = req.getImage();
        }

        ProductEntity updatedProduct = ProductEntity.builder()
                .name(req.getName())
                .price(req.getPrice())
                .importPrice(req.getImportPrice())
                .stock(req.getStock())
                .description(req.getDescription())
                .categories(categoryEntities)
                .brand(brand)
                .image(imageUrl)
                .build();
        log.info("Updated product: {}", updatedProduct);
        productRepository.save(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product: {}", id);

        productRepository.deleteById(id);
        log.info("Deleted product id: {}", id);
    }

    @Override
    public boolean updateStock(Long productId, Integer quantity) {
        Optional<ProductEntity> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            ProductEntity product = productOpt.get();
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    /**
     * Convert Product to ProductResponse
     *
     * @param page
     * @param size
     * @param products
     * @return
     */
    private  ProductPageResponse getProductPageResponse(int page, int size, Page<ProductEntity> products) {
        log.info("Convert Product Page");

        List<ProductResponse> productList = products.stream().map(product -> ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .importPrice(product.getImportPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .description(product.getDescription())
                .categoryResponses(product.getCategories().stream().map(categoryMapper::toCategoryResponse)
                        .collect(Collectors.toSet()))
                .brandResponse(brandMapper.toBrandResponse(product.getBrand()))
                .build()).toList();

        ProductPageResponse response = new ProductPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(products.getTotalElements());
        response.setTotalPages(products.getTotalPages());
        response.setProducts(productList);
        return response;
    }

    private ProductResponse toProductRes(ProductEntity product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .importPrice(product.getImportPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .description(product.getDescription())
                .categoryResponses(product.getCategories().stream().map(categoryMapper::toCategoryResponse)
                        .collect(Collectors.toSet()))
                .brandResponse(brandMapper.toBrandResponse(product.getBrand()))
                .build();
    }
}
