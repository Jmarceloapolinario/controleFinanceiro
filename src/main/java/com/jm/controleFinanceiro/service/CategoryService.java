package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.CategoryRequest;
import com.jm.controleFinanceiro.controller.response.CategoryResponse;
import com.jm.controleFinanceiro.mapper.CategoryMapper;
import com.jm.controleFinanceiro.model.Category;
import com.jm.controleFinanceiro.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @CacheEvict(value = "category", allEntries = true)
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category newCategory = categoryMapper.categoryRequestToCategory(categoryRequest);
        categoryRepository.save(newCategory);
        return categoryMapper.categoryToCategoryResponse(newCategory);
    }

    @Cacheable(value = "category")
    public Page<CategoryResponse> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::categoryToCategoryResponse);
    }

    @Cacheable(value = "category", key = "#id")
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return categoryMapper.categoryToCategoryResponse(category);
    }
}
