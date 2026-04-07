package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.CategoryResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    @Cacheable(value = "categories", key = "#status", unless = "#result==null")
    BaseResponse<List<CategoryResponse>> getCategories(boolean status);
}
