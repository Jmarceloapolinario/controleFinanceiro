package com.jm.controleFinanceiro.mapper;

import com.jm.controleFinanceiro.controller.request.CategoryRequest;
import com.jm.controleFinanceiro.controller.response.CategoryResponse;
import com.jm.controleFinanceiro.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse categoryToCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true)
    Category categoryRequestToCategory(CategoryRequest categoryRequest);
}
