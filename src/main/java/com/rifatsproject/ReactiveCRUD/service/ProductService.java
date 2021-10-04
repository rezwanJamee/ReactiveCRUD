package com.rifatsproject.ReactiveCRUD.service;

import com.rifatsproject.ReactiveCRUD.dto.ProductDto;
import com.rifatsproject.ReactiveCRUD.entity.Product;
import com.rifatsproject.ReactiveCRUD.repository.ProductRepository;
import com.rifatsproject.ReactiveCRUD.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getProducts(){

        return productRepository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return productRepository.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductByPriceRange(double min, double max){
        return productRepository.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id){
        return productRepository.findById(id)
                .flatMap(product -> productDtoMono.map(AppUtils::dtoToEntity)
                .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        return productRepository.deleteById(id);
    }
}
