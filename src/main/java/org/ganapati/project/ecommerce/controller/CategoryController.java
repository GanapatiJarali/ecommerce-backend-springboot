package org.ganapati.project.ecommerce.controller;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.CategoryResponse;
import org.ganapati.project.ecommerce.service.CacheService;
import org.ganapati.project.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final CacheService cacheService;

    @Autowired
    public CategoryController(CategoryService categoryService, CacheService cacheService) {
        this.categoryService = categoryService;
        this.cacheService = cacheService;
    }

    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getCategories(@RequestParam(name = "status", defaultValue = "true", required = false) boolean status) {
        log.info("get categories: {} ", status);
        return ResponseEntity.ok(categoryService.getCategories(status));
    }

    @PutMapping("/clear")
    public ResponseEntity<BaseResponse> clearResponse() {
        cacheService.clearAll();
        return new ResponseEntity<>(BaseResponse.success(null), HttpStatus.OK);
    }

}
