package com.rifatsproject.ReactiveCRUD.utils;

import com.rifatsproject.ReactiveCRUD.dto.ProductDto;
import com.rifatsproject.ReactiveCRUD.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
