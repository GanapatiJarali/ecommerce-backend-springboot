package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.dto.ProductRequest;
import org.ganapati.project.ecommerce.dto.ProductResponse;

public interface ProductService {
     BaseResponse<ProductResponse> addProduct(ProductRequest product);
    public BaseResponse<PageResponse<ProductResponse>> getAllProducts(int page, int size, Integer category, Boolean status);
    BaseResponse<ProductResponse> fetchByProductId(Long id);
    BaseResponse<ProductResponse> updateProduct(ProductRequest product);

}
