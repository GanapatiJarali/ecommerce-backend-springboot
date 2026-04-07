package org.ganapati.project.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.CategoryResponse;
import org.ganapati.project.ecommerce.entity.Category;
import org.ganapati.project.ecommerce.mapper.CategoryMapper;
import org.ganapati.project.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public BaseResponse<List<CategoryResponse>> getCategories(boolean status) {
        log.info("categoryResponse Service: {}", status);
        List<Category> categories = categoryRepository.findByStatus(status);
        log.info("categories : {} ", categories);
        List<CategoryResponse> categoryResponses = categoryMapper.categoryEntityToCategoryResponse(categories);
        log.info("categoryResponse: {}", categoryResponses);
        return BaseResponse.success(categoryResponses);
    }
}
