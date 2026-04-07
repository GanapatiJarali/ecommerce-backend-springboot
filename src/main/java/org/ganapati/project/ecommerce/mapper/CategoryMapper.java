package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.CategoryResponse;
import org.ganapati.project.ecommerce.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  List<CategoryResponse> categoryEntityToCategoryResponse(List<Category> category);
}
